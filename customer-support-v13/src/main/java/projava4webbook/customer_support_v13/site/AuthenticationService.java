package projava4webbook.customer_support_v13.site;

import java.security.Principal;

public interface AuthenticationService {
    Principal authenticate(String username, String password);
}
