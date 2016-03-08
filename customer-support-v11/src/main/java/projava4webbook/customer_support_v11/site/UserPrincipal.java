package projava4webbook.customer_support_v11.site;

import java.io.Serializable;
import java.security.Principal;

import javax.servlet.http.HttpSession;

public class UserPrincipal implements Principal, Cloneable, Serializable {
    private static final long serialVersionUID = -8155258737725150704L;
    
    private static final String PRINCIPAL_SESSION_ATTRIBUTE_NAME = "projava4webbook.customer_support.user.principal";
    
    private final String username;
    
    public UserPrincipal(String username) {
        this.username = username;
    }

    @Override
    public String getName() {
        return username;
    }
    
    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UserPrincipal 
                && ((UserPrincipal) obj).username.equals(this.username);
    }
    
    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException notPossibleException) {
            throw new RuntimeException(notPossibleException);
        }
    }
    
    @Override
    public String toString() {
        return username;
    }
    
    public static Principal getPrincipal(HttpSession session) {
        return session == null 
                ? null 
                : (Principal) session.getAttribute(PRINCIPAL_SESSION_ATTRIBUTE_NAME);
    }
    
    public static void setPrincipal(HttpSession session, Principal principal) {
        session.setAttribute(PRINCIPAL_SESSION_ATTRIBUTE_NAME, principal);
    }
}
