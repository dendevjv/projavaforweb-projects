package projava4webbook.spring_forms.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(
        basePackages = "projava4webbook.spring_forms.site", 
        excludeFilters = @ComponentScan.Filter(Controller.class)
)
public class RootContextConfiguration {

}
