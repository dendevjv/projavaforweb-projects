package projava2webbook.discussion_board.site;

import java.util.List;

import projava2webbook.discussion_board.site.entity.Reply;

public interface ReplyRepository {

    List<Reply> getForDiscussion(long id);
    
    void add(Reply reply);
    
    void update(Reply reply);
    
    void deleteForDiscussion(long id);
    
}
