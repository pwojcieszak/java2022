package pl.edu.uj.sender;

public class EmailMessageProvider extends MessageProvider {

    int actualMessagesCount = 0;

    @Override
    public synchronized Message getNextMessage() {
        if (actualMessagesCount < 100) {
            return new EmailMessage("Title: message nr %d".formatted(++actualMessagesCount), "Lorem ipsum...");
        }
        return null;
    }
}
