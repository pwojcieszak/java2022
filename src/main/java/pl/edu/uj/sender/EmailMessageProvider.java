package pl.edu.uj.sender;

public class EmailMessageProvider extends MessageProvider {

    int messagesCount = 100;

    @Override
    public Message getNextMessage() throws InterruptedException {
        int currentCount;
        synchronized (this) {
            if (messagesCount > 0) {
                currentCount = messagesCount--;
            } else {
                return null; // user does not provide any new messages
            }
        }
        Thread.sleep(1000); // waiting for user to provide message
        return new EmailMessage("Title: message nr " + currentCount, "Body");
    }
}
