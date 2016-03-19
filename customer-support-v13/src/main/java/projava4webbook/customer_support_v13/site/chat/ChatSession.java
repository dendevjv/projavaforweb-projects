package projava4webbook.customer_support_v13.site.chat;

import java.util.function.Consumer;

import javax.websocket.Session;

public class ChatSession {
    private long sessionId;
    private String customerUsername;
    private Session customer;
    private String representativeUsername;
    private Session representative;
    private ChatMessage creationMessage;
    private Consumer<Session> onRepresentativeJoin;

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public Session getCustomer() {
        return customer;
    }

    public void setCustomer(Session customer) {
        this.customer = customer;
    }

    public String getRepresentativeUsername() {
        return representativeUsername;
    }

    public void setRepresentativeUsername(String representativeusername) {
        this.representativeUsername = representativeusername;
    }

    public Session getRepresentative() {
        return representative;
    }

    public void setRepresentative(Session representative) {
        this.representative = representative;
        if (this.onRepresentativeJoin != null) {
            this.onRepresentativeJoin.accept(representative);
        }
    }

    public ChatMessage getCreationMessage() {
        return creationMessage;
    }

    public void setCreationMessage(ChatMessage creationMessage) {
        this.creationMessage = creationMessage;
    }

    public void setOnRepresentativeJoin(Consumer<Session> onRepresentativeJoin) {
        this.onRepresentativeJoin = onRepresentativeJoin;
    }

}
