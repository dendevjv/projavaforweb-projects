package projava4webbook.customer_support_v13.site;

import java.security.Principal;

import org.springframework.validation.annotation.Validated;

import projava4webbook.customer_support_v13.validation.NotBlank;

@Validated
public interface AuthenticationService {
    Principal authenticate(
            @NotBlank(message = "{validate.authenticate.username}")
            String username, 
            @NotBlank(message = "{validate.authenticate.password}")
            String password
    );
}
