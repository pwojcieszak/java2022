package pl.edu.uj.sender;

public class PushSender implements Sender {

    @Override
    public void send(Message message, Recipient recipient) throws SenderException {
        // TODO sprawdź czy message jest klasy PushMessage
        //  oraz recipient klasy PushRecipient
        //  Jeśli nie, throw new SenderException(...)
        if(!((message instanceof PushMessage) && (recipient instanceof PushRecipient))){
            throw new SenderException("Typy PushMessage i/lub PushRecipient sie nie zgadzaja");
        }
        // TODO wywyołaj walidację wiadomości
        message.validateMessage();
        // TODO wywolaj walidację odbiorcy
        recipient.validateRecipient();
        String bodyMD5 = message.anonymizeMessageBody();
        String anonymizedRecipientAddress = recipient.anonymize();

        System.out.printf("[Push] Message sent, title= '%s', bodyMD5= '%s', recipient= '%s'%n", message.getMessageTitle(), bodyMD5, anonymizedRecipientAddress);
    }
}
