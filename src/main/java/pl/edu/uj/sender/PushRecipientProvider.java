package pl.edu.uj.sender;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class PushRecipientProvider extends RecipientProvider {
    List<Recipient> listOfClients = new ArrayList<>();

    @Override
    public Recipient getNextRecipient() {
        int randomIndex = (int) (Math.random() * listOfClients.size());
        return listOfClients.get(randomIndex);
    }

    public void addClient() {
        int recipientLength = (int) (Math.random() * 10) + 8;
        String generatedString = RandomStringUtils.randomAlphabetic(recipientLength);
        listOfClients.add(new PushRecipient(generatedString));
    }

    public void removeClient() {
        listOfClients.remove(listOfClients.size() - 1);
    }

}
