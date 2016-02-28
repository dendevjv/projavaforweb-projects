package projava4webbook.model_view_controller.site;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
    private long userId;
    private String username;
    private String name;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
