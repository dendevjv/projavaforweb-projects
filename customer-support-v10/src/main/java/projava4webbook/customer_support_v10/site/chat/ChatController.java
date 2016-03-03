package projava4webbook.customer_support_v10.site.chat;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("chat")
public class ChatController {
    private static final Logger log = LogManager.getLogger();
    
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Map<String, Object> model) {
        log.debug("Listing pending support chats");
        model.put("sessions", ChatEndpoint.pendingSessions);
        return "chat/list";
    }
    
    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String newChat(Map<String, Object> model, HttpServletResponse response) {
        setNoCacheResponseHeaders(response);
        log.debug("Accepted new chat request.");
        model.put("chatSessionId", 0);
        return "chat/chat";
    }
    
    @RequestMapping(value = "join/{chatSessionId}", method = RequestMethod.POST)
    public ModelAndView joinChat(Map<String, Object> model,
            HttpServletResponse response,
            @PathVariable("chatSessionId") long chatSessionId) {
        setNoCacheResponseHeaders(response);
        log.debug("Pending chat request joined.");
        model.put("chatSessionId", chatSessionId);
        return new ModelAndView("chat/chat");
    }
    
    private void setNoCacheResponseHeaders(HttpServletResponse response) {
        response.setHeader("Expires", "Thu, 1 Jan 1970 12:00:00 GMT");
        response.setHeader("Cache-Control","max-age=0, must-revalidate, no-cache");
        response.setHeader("Pragma", "no-cache");
    }
}
