package pl.edu.uj.sender;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SenderApplication {
  static final int PORT = 27182;
  private static final Logger logger = LoggerFactory.getLogger(SenderApplication.class);

  public static void main(String[] args) {
    PushSender pushSender = new PushSender();
    PushMessageProvider messageProvider = new PushMessageProvider();
    PushRecipientProvider recipientProvider = new PushRecipientProvider();
    List<PushPackage> queue = new ArrayList<>();

    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(PORT);
    } catch (IOException e) {
      System.out.println("Could not listen on port: " + PORT);
      System.exit(-1);
    }
    Socket clientSocket = null;
    while (true) {
      try {
        clientSocket = serverSocket.accept();
        recipientProvider.addClient();
      } catch (IOException e) {
        System.out.println("Accept failed: " + PORT);
        System.exit(-1);
      }

      new Thread(new PushSenderRunnable(queue, pushSender, clientSocket)).start();
      new Thread(new PushEnquerRunnable(messageProvider, recipientProvider, queue, clientSocket)).start();
    }

  }

  private static class PushPackage {

    final Message nextMessage;
    final Recipient nextRecipient;

    public PushPackage(Message nextMessage, Recipient nextRecipient) {
      this.nextMessage = nextMessage;
      this.nextRecipient = nextRecipient;
    }
  }

  private static class PushEnquerRunnable implements Runnable {

    private final PushMessageProvider messageProvider;
    private final PushRecipientProvider recipientProvider;
    private final List<PushPackage> queue;
    private final Socket clientSocket;

    public PushEnquerRunnable(PushMessageProvider messageProvider, PushRecipientProvider recipientProvider, List<PushPackage> queue, Socket clientSocket) {
      this.messageProvider = messageProvider;
      this.recipientProvider = recipientProvider;
      this.queue = queue;
      this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
      Message nextMessage;
      try {
        do {
          nextMessage = messageProvider.getNextMessage();
          if (nextMessage != null) {
            final Recipient nextRecipient = recipientProvider.getNextRecipient();
            synchronized (queue) {
              queue.add(new PushPackage(nextMessage, nextRecipient));
            }
          }
        } while (!clientSocket.isClosed());
        recipientProvider.removeClient();
      } catch (InterruptedException e) {
        logger.error("Couldn't enqueue message", e);
      }
    }
  }

  private static class PushSenderRunnable implements Runnable {

    private final List<PushPackage> queue;
    private final PushSender pushSender;
    private final Socket clientSocket;

    public PushSenderRunnable(List<PushPackage> queue, PushSender pushSender, Socket clientSocket) {
      this.queue = queue;
      this.pushSender = pushSender;
      this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
      ObjectOutputStream out;
      try {
        out = new ObjectOutputStream(clientSocket.getOutputStream());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      do {
        try {
          PushPackage pushPackage = null;
          synchronized (queue) {
            if (!queue.isEmpty()) {
              pushPackage = queue.remove(0);
            }
          }
          if (pushPackage != null) {
            pushSender.send(pushPackage.nextMessage, pushPackage.nextRecipient);
            out.writeObject(pushPackage.nextMessage);
          }
        } catch (SenderException | IOException e) {
          try {
            out.close();
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          }
          try {
            clientSocket.close();
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          }
          logger.info("Client disconnected!");
        }
      } while (!clientSocket.isClosed());
    }
  }
}
