package pl.edu.uj.sender;

import static java.lang.Thread.sleep;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SenderApplication {

  private static final Logger logger = LoggerFactory.getLogger(SenderApplication.class);

  public static void main(String[] args) throws Exception {

    if (args.length == 2) {
      final int numberOfEnqueuingThreads = Integer.parseInt(args[0]);
      final int numberOfSendingThreads = Integer.parseInt(args[1]);
      logger.info("There will be %d enqueuing threads and %d sender threads".formatted(numberOfEnqueuingThreads, numberOfSendingThreads));

      EmailSender emailSender = new EmailSender();
      EmailMessageProvider messageProvider = new EmailMessageProvider();
      RecipientProvider recipientProvider = new EmailRecipientProvider();

      DB db = new DB();
      db.connect("jdbc:mysql://localhost:3306/uj_sender", "sender-app", "My-secret-pass");

      List<Thread> threads = new ArrayList<>();
      for (int i = 0; i < numberOfEnqueuingThreads; i++) {
        threads.add(new Thread(new EmailEnquerRunnable(messageProvider, recipientProvider, db)));
      }
      for (int i = 0; i < numberOfSendingThreads; i++) {
        threads.add(new Thread(new EmailSenderRunnable(db, emailSender)));
      }

      for (Thread thread : threads) {
        thread.start();
      }
      for (Thread thread : threads) {
        thread.join();
      }
      db.disconnect();
    } else {
      logger.error("Params should be: enqueuing-threads-count sender-threads-count");
      System.exit(-1);
    }
  }


  private static class EmailEnquerRunnable implements Runnable {

    private final EmailMessageProvider messageProvider;
    private final RecipientProvider recipientProvider;
    private final DB db;

    public EmailEnquerRunnable(EmailMessageProvider messageProvider, RecipientProvider recipientProvider, DB db) {
      this.messageProvider = messageProvider;
      this.recipientProvider = recipientProvider;
      this.db = db;
    }

    @Override
    public void run() {
      Message nextMessage;
      try {
        do {
          nextMessage = messageProvider.getNextMessage();
          if (nextMessage != null) { // Wyślemy 100 wiadomości
            long email_message_id = db.executeUpdate("INSERT INTO email_message(message_title, message_body) " +
                            "VALUES ('" + nextMessage.getMessageTitle() + "', '" + nextMessage.getMessageBody() + "');"
                    , true);
            final Recipient nextRecipient = recipientProvider.getNextRecipient();
            long email_recipient_id = db.executeUpdate("INSERT INTO email_recipient(recipient_address) " +
                            "SELECT '" + nextRecipient.getRecipientAddress() + "'  WHERE NOT EXISTS (SELECT recipient_address " +
                            "FROM email_recipient WHERE recipient_address = '" + nextRecipient.getRecipientAddress() + "');"
                    , true);
            if (email_recipient_id == 0) {
              ResultSet rs = db.executeQuery("SELECT email_recipient_id " +
                      "FROM email_recipient WHERE recipient_address = '" + nextRecipient.getRecipientAddress() +
                      "';");
              rs.next();
              email_recipient_id = rs.getLong("email_recipient_id");
              rs.close();
            }

            logger.info("Enqueueing message.");
            db.executeUpdate("INSERT INTO uj_sender.email_queue (email_message_id,email_recipient_id) " +
                    "VALUES (" + email_message_id + "," + email_recipient_id + ");", false);
          }
        } while (nextMessage != null);
      } catch (InterruptedException e) {
        logger.error("Couldn't enqueue message", e);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  private static class EmailSenderRunnable implements Runnable {

    private final DB db;
    private final EmailSender emailSender;
    private int counter = 0;

    public EmailSenderRunnable(DB db, EmailSender emailSender) {
      this.db = db;
      this.emailSender = emailSender;
    }

    @Override
    public void run() {
      do {
        try {
          ResultSet rs = db.executeQuery("SELECT * FROM email_queue WHERE status_id = 0 LIMIT 1;");
          if (rs.next()) {
            db.executeUpdate("UPDATE uj_sender.email_queue SET status_id = 1 WHERE email_queue_id  = " +
                    rs.getString(1) + " AND status_id = 0;", false);
            logger.info("Getting message package to send.");
            EmailQueue queue = new EmailQueue(rs.getLong(1), rs.getTimestamp(2),
                    rs.getTimestamp(3), rs.getLong(4), rs.getLong(5),
                    rs.getLong(6));
            rs = db.executeQuery("SELECT * FROM email_message WHERE email_message_id = " +
                    queue.getEmailMessageId() + ";");
            rs.next();
            EmailMessage message = new EmailMessage(rs.getLong(1), rs.getString(2),
                    rs.getString(3), rs.getTimestamp(4));
            rs = db.executeQuery("SELECT * FROM email_recipient WHERE email_recipient_id = " +
                    queue.getEmailRecipientId() + ";");
            rs.next();
            EmailRecipient recipient = new EmailRecipient(rs.getLong(1), rs.getString(2),
                    rs.getTimestamp(3));
            logger.info("Delivering message to send.");
            emailSender.send(message, recipient);
            db.executeUpdate("DELETE FROM email_queue WHERE email_queue_id = " + queue.getEmailQueueId() + ";"
                    , false);
            counter = 0;
          } else {
            logger.info("No email to send, waiting.");
            counter++;
            sleep(1000); // wait for new element in the queue
          }
          rs.close();
        } catch (SenderException | InterruptedException e) {
          logger.error("Couldn't send a message", e);
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      } while (counter < 100);
    }
  }
}
