package projava2webbook.discussion_board.site;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import projava2webbook.discussion_board.site.entity.Discussion;

@Repository
public class InMemoryDiscussionRepository implements DiscussionRepository {
    private final Map<Long, Discussion> database = new Hashtable<>();
    private volatile long discussionIdSequence = 1L;
    @Inject
    ReplyRepository replyRepository;

    @Override
    public List<Discussion> getAll() {
        return new ArrayList<>(this.database.values());
    }

    @Override
    public Discussion get(long id) {
        return this.database.get(id);
    }

    @Override
    public void add(Discussion discussion) {
        discussion.setId(getNextDiscussionId());
        this.database.put(discussion.getId(), discussion);
    }

    @Override
    public void update(Discussion discussion) {
        this.database.put(discussion.getId(), discussion);
    }

    @Override
    public void delete(long id) {
        this.database.remove(id);
        replyRepository.deleteForDiscussion(id);
    }

    private synchronized long getNextDiscussionId() {
        return this.discussionIdSequence++;
    }
}
