package pl.edu.uj.sender;

public abstract class Recipient {
    abstract String getRecipientAddress();

    abstract void validateRecipient() throws SenderException;

    abstract String anonymize();
}
