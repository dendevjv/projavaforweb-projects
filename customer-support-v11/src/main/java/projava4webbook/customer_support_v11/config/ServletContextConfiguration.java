package projava4webbook.customer_support_v11.config;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.RequestToViewNameTranslator;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Controller;

@Configuration
@EnableWebMvc
@ComponentScan(
        basePackages = "projava4webbook.customer_support_v11.site",
        useDefaultFilters = false,
        includeFilters = @ComponentScan.Filter(Controller.class) 
)
public class ServletContextConfiguration extends WebMvcConfigurerAdapter {
    @Inject 
    ObjectMapper objectMapper;
    @Inject 
    Marshaller marshaller;
    @Inject 
    Unmarshaller unmarshaller;
    
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
        converters.add(new FormHttpMessageConverter());
        converters.add(new SourceHttpMessageConverter<>());
        
        MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
        xmlConverter.setSupportedMediaTypes(Arrays.asList(
                new MediaType("application", "xml"),
                new MediaType("text", "xml")
        ));
        xmlConverter.setMarshaller(this.marshaller);
        xmlConverter.setUnmarshaller(this.unmarshaller);
        converters.add(xmlConverter);
        
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setSupportedMediaTypes(Arrays.asList(
                new MediaType("application", "json"),
                new MediaType("text", "json")
        ));
        jsonConverter.setObjectMapper(this.objectMapper);
        converters.add(jsonConverter);
    }
    
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer)
    {
        configurer.favorPathExtension(true).favorParameter(false)
                .parameterName("mediaType").ignoreAcceptHeader(false)
                .useJaf(false).defaultContentType(MediaType.APPLICATION_XML)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("json", MediaType.APPLICATION_JSON);
    }
    
    /**
     * Creates a <code>ViewResolver</code> instance that can match a view name to an actual view.
     */
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/jsp/view/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
    
    /**
     * Creates a bean that translates the request into a view name. 
     * Strips off the web application context URL and any file extension at the end of the URL.
     * It used if the controller method returns a model or model attribute instead of a view or view name.
     */
    @Bean
    public RequestToViewNameTranslator viewNameTranslator() {
        return new DefaultRequestToViewNameTranslator();
    }

    /**
     * Part of enabling multipart support. Tells whether to use Servlet 3.0+
     * multipart support or some third-party tool.<br />
     * <br />
     * Enabling multipart support on the <code>DispatcherServlet</code> is not quite enough
     * to get file uploads working with Spring MVC. Spring Framework also
     * supports older versions of the Servlet API. Before Servlet
     * 3.0, the Servlet API did not have built-in multipart support and
     * third-party tools were necessary to accomplish file uploads. Spring MVC
     * needs a MultipartResolver instance to tell it whether to use Servlet 3.0+
     * multipart support or some third-party tool. This additional @Bean method serves for 
     * creating that bean.
     * 
     * @return MultipartResolver instance
     */
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
