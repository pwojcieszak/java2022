package pl.edu.uj.sender;

import java.time.LocalDateTime;

import static java.lang.Thread.sleep;

public class PushMessageProvider extends MessageProvider {

    @Override
    public synchronized Message getNextMessage() throws InterruptedException {
        sleep(1000);
        return new PushMessage("Example push message", "Now is " + LocalDateTime.now());
    }
}
