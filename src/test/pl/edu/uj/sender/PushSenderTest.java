package pl.edu.uj.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PushSenderTest {
    @Test
    public void testPushSentCorrectly() throws SenderException {
        PushMessage message = new PushMessage("Title", "Body");
        PushRecipient recipient = new PushRecipient("pushRecipient123");
        PushSender push = new PushSender();
        push.send(message, recipient);
    }
    @Test
    public void testMessageAndRecipientType(){
        Exception exception = assertThrows(SenderException.class, () -> {
            new PushSender().send(new EmailMessage("Title", "Body"), new PushRecipient("AdamNowak123"));
        });
        String actualMessage = exception.getMessage();

        String expectedMessage = "Typy PushMessage i/lub PushRecipient sie nie zgadzaja";
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        exception = assertThrows(SenderException.class, () -> {
            new PushSender().send(new PushMessage("Title", "Body"), new EmailRecipient("AdamNowak123@gmail.com"));
        });
        actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        exception = assertThrows(SenderException.class, () -> {
            new PushSender().send(new PushMessage("Title", "Body"), null);
        });
        actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        exception = assertThrows(SenderException.class, () -> {
            new PushSender().send(null, new PushRecipient("AdamNowak123@gmail.com"));
        });
        actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testPushMessageValidation() {
        Exception exception = assertThrows(SenderException.class, () -> {
            new PushMessage("", "Body").validateMessage();
        });
        String actualMessage = exception.getMessage();

        String expectedMessage = "messsageTitle i/lub messageBody sa niepoprawne";
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        exception = assertThrows(SenderException.class, () -> {
            new PushMessage("Title", "").validateMessage();
        });
        actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        exception = assertThrows(SenderException.class, () -> {
            new PushMessage("Title", "Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                    " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam," +
                    " quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute" +
                    " irure dolor in").validateMessage();
        });
        actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testPushRecipientValidation() {
        Exception exception = assertThrows(SenderException.class, () -> {
            new PushRecipient("piotrnowak123!").validateRecipient();
        });
        String actualMessage = exception.getMessage();

        String expectedMessage = "Adres push jest niepoprawny";
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        exception = assertThrows(SenderException.class, () -> {
            new PushRecipient("Loremipsumdolorsitametconsectetu").validateRecipient();
        });
        actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        exception = assertThrows(SenderException.class, () -> {
            new PushRecipient("").validateRecipient();
        });
        actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

}
