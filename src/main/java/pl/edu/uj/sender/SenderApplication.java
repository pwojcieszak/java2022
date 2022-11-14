package pl.edu.uj.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SenderApplication {

    private static final Logger logger = LoggerFactory.getLogger(SenderApplication.class);

    public static void main(String[] args) throws SenderException, InterruptedException {
        // TODO utwórz przykładową wiadomość email oraz push i wyślij
        //  Zaloguj (logger.info(...)) wysłanie wiadomości.
        String title = "Tytul wiadomosci";
        String body = "Ala ma kota, kot ma Ale";
        
        EmailMessage message = new EmailMessage(title, body);
        EmailRecipient recipient = new EmailRecipient("asdfe2312@gmail.com");
        EmailSender email = new EmailSender();
        email.send(message, recipient);
        logger.info("Wyslano wiadomosc email");

        PushMessage message2 = new PushMessage(title, body);
        PushRecipient recipient2 = new PushRecipient("AdamNowak123");
        PushSender push = new PushSender();
        push.send(message2, recipient2);
        logger.info("Wyslano wiadomosc push");
    }
}
