package pl.edu.uj.sender;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class EmailRecipientProvider extends RecipientProvider {

    Random random = new Random();

    @Override
    public synchronized Recipient getNextRecipient() {
        return new EmailRecipient(RandomStringUtils.randomAlphanumeric(7) + "@" +
                RandomStringUtils.randomAlphanumeric(2) + "." + RandomStringUtils.randomAlphanumeric(2));
    }
}
