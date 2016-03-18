package projava4webbook.custom_constraint.config;

import java.nio.charset.StandardCharsets;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
@ComponentScan(basePackages = "projava4webbook.custom_constraint.site", excludeFilters = @ComponentScan.Filter(Controller.class) )
public class RootContextConfiguration {
    
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setCacheSeconds(-1);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setBasenames("/WEB-INF/i18n/titles", "/WEB-INF/i18n/messages", 
                "/WEB-INF/i18n/errors", "/WEB-INF/i18n/validation");
        return messageSource;
    }
    
    /**
     * Configures Spring Validator Bean.<br />
     * <br />
     * The <code>LocalValidatorFactoryBean</code> automatically detects the Bean Validation implementation on
     * the classpath, whether that’s Hibernate Validator or some other implementation, and uses its default
     * <code>javax.validation.ValidatorFactory</code> as the backing factory.<br />
     * <br />
     * Configures the <code>LocalValidatorFactoryBean</code> to use <code>MessageSource</code> bean. 
     */
    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(this.messageSource());
        return validator;
    }
    
    /**
     * Creates instance of <code>MethodValidationPostProcessor</code> to support validation
     * of method arguments and return values.<br />
     * Configure it to use the <code>LocalValidatorFactoryBean</code> created earlier.
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        processor.setValidator(this.localValidatorFactoryBean());
        return processor;
    }
}
