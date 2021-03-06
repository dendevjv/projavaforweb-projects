package projava2webbook.discussion_board.site;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class FakeNotificationService implements NotificationService {
    private static final Logger log = LogManager.getLogger();

    @Override
    @Async
    public void sendNotification(String subject, String message, Collection<String> recipients) {
        log.info("Started notifying recipients {}.", recipients);
        try {
            Thread.sleep(5_000L);
        } catch (InterruptedException ignore) {
        }
        log.info("Finished notifying recipients {}.", recipients);
    }

}
