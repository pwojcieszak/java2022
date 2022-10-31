package pl.edu.uj.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmailSenderTest {
    @Test
    public void testMessageAndRecipientType(){
        Exception exception = assertThrows(SenderException.class, () -> {
            new EmailSender().send(new PushMessage("Title", "Body"), new EmailRecipient("adam@gmail.com"));
        });
        String actualMessage = exception.getMessage();

        String expectedMessage = "Typy EmailMessage i/lub EmailRecipient sie nie zgadzaja";
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        exception = assertThrows(SenderException.class, () -> {
            new EmailSender().send(new EmailMessage("Title", "Body"), new PushRecipient("AdamNowak123"));
        });
        actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        exception = assertThrows(SenderException.class, () -> {
            new EmailSender().send(null, new EmailRecipient("AdamNowak123"));
        });
        actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        exception = assertThrows(SenderException.class, () -> {
            new EmailSender().send(new EmailMessage("Title", "Body"), null);
        });
        actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testEmailMessageValidation() {
        Exception exception = assertThrows(SenderException.class, () -> {
            new EmailMessage("", "Body").validateMessage();
        });
        String actualMessage = exception.getMessage();

        String expectedMessage = "messsageTitle i/lub messageBody sa niepoprawne";
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        exception = assertThrows(SenderException.class, () -> {
            new EmailMessage("Title", "").validateMessage();
        });
        actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testEmailRecipientValidation() {
        Exception exception = assertThrows(SenderException.class, () -> {
            new EmailRecipient("AdamNowak123").validateRecipient();
        });
        String actualMessage = exception.getMessage();

        String expectedMessage = "Adres email jest niepoprawny";
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        exception = assertThrows(SenderException.class, () -> {
            new EmailRecipient("AdamNowak123@").validateRecipient();
        });
        actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        exception = assertThrows(SenderException.class, () -> {
            new EmailRecipient("@AdamNowak123").validateRecipient();
        });
        actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        exception = assertThrows(SenderException.class, () -> {
            new EmailRecipient("Adam@@Nowak123").validateRecipient();
        });
        actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        exception = assertThrows(SenderException.class, () -> {
            new EmailRecipient("").validateRecipient();
        });
        actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

}
