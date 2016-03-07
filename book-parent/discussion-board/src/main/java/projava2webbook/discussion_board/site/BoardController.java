package projava2webbook.discussion_board.site;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import projava2webbook.discussion_board.site.entity.Discussion;
import projava2webbook.discussion_board.site.form.DiscussionForm;

@Controller
@RequestMapping("discussion")
public class BoardController {
    @Inject
    DiscussionService discussionService;
    
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String listDiscussions(Map<String, Object> model) {
        model.put("discussions", discussionService.getAllDiscussions());
        return "discussion/list";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createDiscussion(Map<String, Object> model) {
        model.put("discussionForm", new DiscussionForm());
        return "discussion/create";
    }
    
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public View createDiscussion(DiscussionForm form) {
        Discussion discussion = new Discussion();
        discussion.setUser(form.getUser());
        discussion.setSubject(form.getSubject());
        discussion.setMessage(form.getMessage());

        this.discussionService.saveDiscussion(discussion);

        return new RedirectView("/discussion/" + discussion.getId() + "/" + discussion.getUriSafeSubject(), true,
                false);
    }
}
