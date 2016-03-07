package projava2webbook.discussion_board.site;

import java.util.Collection;

import org.springframework.scheduling.annotation.Async;

public interface NotificationService {
    @Async
    void sendNotification(String subject, String message, Collection<String> recipients);
}
