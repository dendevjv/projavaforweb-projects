package projava4webbook.customer_support_v12.site;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("session")
public class SessionListController {
    @Inject
    SessionRegistryInterface sessionRegistry;
    
    @RequestMapping(value="list", method = RequestMethod.GET)
    public String list(Map<String, Object> model) {
        model.put("timestamp", System.currentTimeMillis());
        model.put("numberOfSessions", sessionRegistry.getNumberOfSessions());
        model.put("sessionList", sessionRegistry.getAllSessions());
        
        return "session/list";
    }
}
