package projava4webbook.spring_java_config.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.stereotype.Controller;

@Configuration
@EnableWebMvc
@ComponentScan(
        basePackages = "projava4webbook.spring_java_config.site", 
        useDefaultFilters = false, 
        includeFilters = @ComponentScan.Filter(Controller.class)
)
public class ServletContextConfiguration {
}
