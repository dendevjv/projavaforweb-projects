package projava2webbook.discussion_board.site;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import projava2webbook.discussion_board.site.entity.Reply;

@Repository
public class InMemoryReplyRepository implements ReplyRepository {
    private final Map<Long, Reply> database = new Hashtable<>();
    private volatile long replyIdSequence = 1L;

    @Override
    public List<Reply> getForDiscussion(long id) {
        ArrayList<Reply> list = new ArrayList<>(this.database.values());
        list.removeIf((r) -> r.getDiscussionId() != id);
        return list;
    }

    @Override
    public synchronized void add(Reply reply) {
        reply.setId(this.getNextReplyId());
        database.put(reply.getId(), reply);
    }

    @Override
    public synchronized void update(Reply reply) {
        database.put(reply.getId(), reply);
    }

    @Override
    public synchronized void deleteForDiscussion(long id) {
        database.entrySet().removeIf(e -> e.getValue().getDiscussionId() == id);
    }

    
    private synchronized long getNextReplyId() {
        return this.replyIdSequence++;
    }
}
