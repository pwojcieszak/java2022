package pl.edu.uj.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import static java.lang.Thread.sleep;

public class PushClient {
    private static final Logger logger = LoggerFactory.getLogger(SenderApplication.class);
    static final int PORT = 27182;

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Socket pushSocket = null;
        ObjectInputStream in = null;
        PushMessage message;

        try {
            pushSocket = new Socket("localhost", PORT);
            in = new ObjectInputStream(
                    pushSocket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to: localhost.");
            System.exit(1);
        }
        while (true) {
            message = (PushMessage) in.readObject();
            if (message.getMessageBody() != null) {
                logger.info(message.getMessageBody());
                sleep(1000);
            } else break;
        }
        in.close();
        pushSocket.close();
    }
}
