package projava4webbook.spring_hybrid_config;

import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl implements GreetingService {

    @Override
    public String getGreeting(String name) {
        return "Hi, " + name;
    }

}
