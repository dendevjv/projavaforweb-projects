package projava4webbook.customer_support_v12.site.chat;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("chat")
public class ChatController {
    @Inject
    ChatService chatService;
    
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Map<String, Object> model) {
        model.put("sessions", chatService.getPendingSessions());
        return "chat/list";
    }
    
    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String newChat(Map<String, Object> model, HttpServletResponse response) {
        setNoCacheResponseHeaders(response);
        model.put("chatSessionId", 0);
        return "chat/chat";
    }
    
    @RequestMapping(value = "join/{chatSessionId}", method = RequestMethod.POST)
    public String joinChat(Map<String, Object> model,
            HttpServletResponse response,
            @PathVariable("chatSessionId") long chatSessionId) {
        setNoCacheResponseHeaders(response);
        model.put("chatSessionId", chatSessionId);
        return "chat/chat";
    }
    
    private void setNoCacheResponseHeaders(HttpServletResponse response) {
        response.setHeader("Expires", "Thu, 1 Jan 1970 12:00:00 GMT");
        response.setHeader("Cache-Control","max-age=0, must-revalidate, no-cache");
        response.setHeader("Pragma", "no-cache");
    }
}
