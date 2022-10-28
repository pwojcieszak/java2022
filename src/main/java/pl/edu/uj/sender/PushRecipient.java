package pl.edu.uj.sender;

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


    @Override
    String anonymize() {
        // Uzupełnij kod do anonimizacji:
        String anonymizedRecipientAddress = "...ABC";
        return anonymizedRecipientAddress;
    }
}
