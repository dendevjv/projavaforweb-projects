package projava4webbook.customer_support_v12.site;

import java.util.Hashtable;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<String, String> userDatabase = new Hashtable<>();
    
    public InMemoryUserRepository() {
        userDatabase.put("Nick", "miller");
        userDatabase.put("Jess", "teacher");
        userDatabase.put("Couch", "teacher");
    }

    @Override
    public String getPasswordForUser(String username) {
        return this.userDatabase.get(username);
    }

}
