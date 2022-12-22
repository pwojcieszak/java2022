package pl.edu.uj.sender;

import java.util.Random;

public class EmailRecipientProvider extends RecipientProvider {
    Random random = new Random();

    @Override
    public Recipient getNextRecipient() {
        final int i = random.nextInt(3);
        return switch (i) {
            case 0 -> new EmailRecipient("MR.Smith@example.com");
            case 1 -> new EmailRecipient("john23@gmail.com");
            case 2 -> new EmailRecipient("aliceinchains@yahoo.com");
            default -> throw new IllegalArgumentException();
        };
    }
}
