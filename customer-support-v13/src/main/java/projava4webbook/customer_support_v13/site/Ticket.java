package projava4webbook.customer_support_v13.site;

import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import projava4webbook.customer_support_v13.validation.NotBlank;

public class Ticket {
    private long id;
    
    @NotBlank(message = "{validate.ticket.customerName}")
    private String customerName;

    @NotBlank(message = "{validate.ticket.subject}")
    private String subject;

    @NotBlank(message = "{validate.ticket.body}")
    private String body;
    
    private Instant dateCreated;

    @Valid
    private Map<String, Attachment> attachments = new LinkedHashMap<>();
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Attachment getAttachment(String name) {
        return this.attachments.get(name);
    }

    public Collection<Attachment> getAttachments() {
        return this.attachments.values();
    }

    public void addAttachment(Attachment attachment) {
        this.attachments.put(attachment.getName(), attachment);
    }

    public int getNumberOfAttachments() {
        return this.attachments.size();
    }

    
    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }
    
}
