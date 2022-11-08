package pl.edu.uj.restsender.service;

import org.springframework.stereotype.Service;
import pl.edu.uj.sender.*;

@Service("SimpleRestApiService")
public class RestSenderService {
    public String sendEmail(String address, String title, String body){
        EmailSender email = new EmailSender();
        try {
            email.send(new EmailMessage(title, body), new EmailRecipient(address));
            return "OK";
        } catch (SenderException | InterruptedException e) {
            return e.getMessage();
        }
    }

    public String sendPush(String address, String title, String body){
        PushSender push = new PushSender();
        try {
            push.send(new PushMessage(title, body), new PushRecipient(address));
            return "OK";
        } catch (SenderException e) {
            return e.getMessage();
        }
    }
}
