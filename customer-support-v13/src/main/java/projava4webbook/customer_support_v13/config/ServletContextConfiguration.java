package projava4webbook.customer_support_v13.config;

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
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.RequestToViewNameTranslator;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Controller;

@Configuration
@EnableWebMvc
@ComponentScan(
        basePackages = "projava4webbook.customer_support_v13.site",
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
     * Adds a <code>LocaleChangeInterceptor</code>. It changes the locale when requested.<br />
     * On each request to the <code>DispatcherServlet</code>, it looks for a request parameter, 
     * which defaults to <code>locale</code> but can be customized. 
     * If this request parameter exists, the interceptor converts the <code>String</code> parameter to a
     * <code>Locale</code> and then uses the <code>LocaleResolver</code>’s <code>setLocale</code> method 
     * to set the locale.<br />
     * <br />
     * On any page you can add a link to change locales and simply submit it to the current page.
     * This not only changes the locale for the current page, it also changes the locale for all subsequent
     * pages the user visits until his session times out or he closes his browser.
     * <br />
     * Example usage: <code>http://localhost:8080/i18n/<strong>?locale=es_MX</strong></code><br />
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        
        registry.addInterceptor(new LocaleChangeInterceptor());
    }
    
    /**
     * Creates a locale resolver for determining the locale for the current request so that it can 
     * determine how to localize messages.<br />
     * <code>SessionLocaleResolver</code> uses the following strategy:<br />
     * <ul>
     *      <li><code>SessionLocaleResolver</code> looks on the current session for the session attribute whose name
     *          is equal to the <code>SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME</code> constant. If
     *          the attribute exists its value is returned.</li>
     *      <li><code>SessionLocaleResolver</code> next checks whether its <code>defaultLocale</code> property is set and
     *          returns it if it is.</li>
     *      <li>Finally, <code>SessionLocaleResolver</code> returns the value of <code>getLocale</code> on the
     *          <code>HttpServletRequest</code> (which comes from the <code>Accept-Language header</code>).</li>
     * </ul>
     * The <code>DispatcherServlet</code> detects the resolver and automatically uses it for all locale-fetching actions.
     * For example, your request handler methods may have a parameter of type <code>Locale</code>, and Spring
     * automatically uses the value provided by the <code>LocaleResolver</code> to supply that argument.
     * @return an instance of <code>SessionLocaleResolver</code>
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new SessionLocaleResolver();
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
