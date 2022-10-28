package pl.edu.uj.sender;

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


    @Override
    String anonymize() {
        // TODO Uzupełnij kod do anonimizacji:
        String anonymizedRecipientAddress = "***@TODO";
        return anonymizedRecipientAddress;
    }
}
