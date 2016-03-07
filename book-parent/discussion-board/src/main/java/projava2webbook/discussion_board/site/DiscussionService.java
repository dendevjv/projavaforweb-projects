package projava2webbook.discussion_board.site;

import java.util.List;

import projava2webbook.discussion_board.site.entity.Discussion;

public interface DiscussionService {
    
    List<Discussion> getAllDiscussions();
    
    Discussion getDiscussion(long id);
    
    void saveDiscussion(Discussion discussion);
    
}
