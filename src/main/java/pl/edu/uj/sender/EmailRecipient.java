package pl.edu.uj.sender;

import java.util.regex.Pattern;

public class EmailRecipient extends Recipient {

    private final String recipientAddress;


    public EmailRecipient(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    // TODO zaimplementuj validateRecipient() do sprawdzania poprawności adresu email
    //  (czyli np czy jest jednokrotny symbol @).
    //  W przypadku błędu, rzuć wyjątkiem.
    public void validateRecipient() throws SenderException{
        if(!(Pattern.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", recipientAddress))){
            throw new SenderException("Adres email jest niepoprawny");
        }
    }


    @Override
    String anonymize() {
        // TODO Uzupełnij kod do anonimizacji:
        return recipientAddress.replaceAll(".(?=[^@]*?@)", "*");
    }
}
