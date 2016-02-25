package projava4webbook.spring_hybrid_config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    private GreetingService greetingService;

    @ResponseBody
    @RequestMapping("/hello")
    public String helloWorld() {
        return "Hello, Spring Framework with hybrid configuration!";
    }

    @ResponseBody
    @RequestMapping(value = "/hello", params = {"name"})
    public String helloName(@RequestParam("name") String name) {
        return greetingService.getGreeting(name);
    }

    @Autowired
    public void setGreetingService(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

}
