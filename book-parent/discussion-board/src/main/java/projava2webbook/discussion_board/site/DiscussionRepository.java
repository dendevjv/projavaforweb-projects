package projava2webbook.discussion_board.site;

import java.util.List;

import projava2webbook.discussion_board.site.entity.Discussion;

public interface DiscussionRepository {
    
    List<Discussion> getAll();
    
    Discussion get(long id);
    
    void add(Discussion discussion);
    
    void update(Discussion discussion);
    
    void delete(long id);
    
}
