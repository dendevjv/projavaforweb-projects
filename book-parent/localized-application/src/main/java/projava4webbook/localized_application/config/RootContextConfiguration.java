package projava4webbook.localized_application.config;

import java.nio.charset.StandardCharsets;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(
        basePackages = "projava4webbook.localized_application.site",
        excludeFilters = @ComponentScan.Filter(Controller.class) 
)
public class RootContextConfiguration {
    /** 
     * Creates a message source for resolving messages.
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setCacheSeconds(-1);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setBasenames(
                "/WEB-INF/i18n/messages", 
                "/WEB-INF/i18n/errors");
        return messageSource;
    }
}
