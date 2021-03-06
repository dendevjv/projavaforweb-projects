package projava2webbook.discussion_board.site;

import java.text.Normalizer;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import projava2webbook.discussion_board.site.entity.Discussion;

@Service
public class DefaultDiscussionService implements DiscussionService {
    private static final Logger log = LogManager.getLogger();
    
    @Inject
    DiscussionRepository discussionRepository;

    @Override
    public List<Discussion> getAllDiscussions() {
        List<Discussion> list = this.discussionRepository.getAll();
        list.sort((d1, d2) -> d1.getLastUpdated().compareTo(d2.getLastUpdated()));
        return list;
    }

    @Override
    public Discussion getDiscussion(long id) {
        return this.discussionRepository.get(id);
    }

    @Override
    public void saveDiscussion(Discussion discussion) {
        String subject = discussion.getSubject();
        subject = Normalizer.normalize(subject.toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replaceAll("[^\\p{Alnum}]+", "-")
                .replace("--", "-").replace("--", "-")
                .replaceAll("[^a-z0-9]+$", "")
                .replaceAll("^[^a-z0-9]+", "");
        discussion.setUriSafeSubject(subject);
        
        Instant now = Instant.now();
        discussion.setLastUpdated(now);
        
        if (discussion.getId() < 1) { // if the discussion is new
            discussion.setCreated(now);
            discussion.getSubscribedUsers().add(discussion.getUser());
            this.discussionRepository.add(discussion);
        } else {
            this.discussionRepository.update(discussion);
        }
    }

    @Scheduled(fixedDelay = 15_000L, initialDelay = 15_000L)
    public void deleteStaleDiscussions() {
        Instant oneYearAgo = Instant.now().minus(365L, ChronoUnit.DAYS);
        log.info("Deleting discussions stale since {}.", oneYearAgo);

        List<Discussion> list = this.discussionRepository.getAll();
        list.removeIf(d -> d.getLastUpdated().isAfter(oneYearAgo));

        for (Discussion old : list) {
            this.discussionRepository.delete(old.getId());
        }
    }
}
