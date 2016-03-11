package projava4webbook.localized_application.site;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import projava4webbook.localized_application.exception.InternationalizedException;

@Controller
public class HomeController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Map<String, Object> model) {
        model.put("date", new Date());
        model.put("alerts", 12);
        model.put("numCritical", 0);
        model.put("numImportant", 11);
        model.put("numTrivial", 1);
        model.put("exception", new InternationalizedException(
                "bad.food.exception", "You ate bad food."
        ));
        
        return "home/index";
    }
}
