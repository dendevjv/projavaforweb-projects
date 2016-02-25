package projava4webbook.spring_one_context_xml_config;

public class StudyGreetingServiceImpl implements GreetingService {

    @Override
    public String getGreeting(String name) {
        return "Hello, " + name + "! Study Spring Framework!";
    }

}
