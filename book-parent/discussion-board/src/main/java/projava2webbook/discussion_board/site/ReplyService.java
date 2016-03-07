package projava2webbook.discussion_board.site;

import java.util.List;

import projava2webbook.discussion_board.site.entity.Reply;

public interface ReplyService {

    List<Reply> getRepliesForDiscussion(long discussionId);
    
    void saveReply(Reply reply);
    
}
