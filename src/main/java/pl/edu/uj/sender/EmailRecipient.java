package pl.edu.uj.sender;

import java.sql.Timestamp;
import java.util.regex.Pattern;

public class EmailRecipient extends Recipient {

    final long emailMessageId;
    private final String recipientAddress;
    final Timestamp creationDate;


    public EmailRecipient(String recipientAddress) {
        this.emailMessageId = 0;
        this.recipientAddress = recipientAddress;
        this.creationDate = null;
    }

    public EmailRecipient(long emailMessageId, String recipientAddress, Timestamp creationDate) {
        this.emailMessageId = emailMessageId;
        this.recipientAddress = recipientAddress;
        this.creationDate = creationDate;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void validateRecipient() throws SenderException {
        if (!(Pattern.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", recipientAddress))) {
            throw new SenderException("Adres email jest niepoprawny");
        }
    }


    @Override
    String anonymize() {
        return recipientAddress.replaceAll(".(?=[^@]*?@)", "*");
    }
}
