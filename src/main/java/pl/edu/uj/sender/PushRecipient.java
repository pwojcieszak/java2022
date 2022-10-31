package pl.edu.uj.sender;

import java.util.regex.Pattern;

public class PushRecipient extends Recipient {
    private final String recipientAddress;

    public PushRecipient(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    @Override
    public String getRecipientAddress() {
        return recipientAddress;
    }

    // TODO zaimplementuj validateRecipient() - sprawdzenie poprawności adresu push.
    //  Sprawdzanie recipientAddress: możesz użyć wyrażenia regularnego:
    //  Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");
    //  W przypadku błędu, rzuć wyjątkiem.
    public void validateRecipient() throws SenderException{
            if(!Pattern.matches("^[a-zA-Z0-9]+$", recipientAddress) || (recipientAddress.length() >= 32)){
            throw new SenderException("Adres push jest niepoprawny");
        }
    }

    @Override
    String anonymize() {
        // TODO Uzupełnij kod do anonimizacji:
        int length = recipientAddress.length();
        String anonymizedRecipientAddress = recipientAddress.replaceAll(".(?=.{5})", "*");;
        return anonymizedRecipientAddress;
    }
}
