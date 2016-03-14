package projava4webbook.customer_support_v12.site;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryTicketRepository implements TicketRepository {
    private volatile int TICKET_ID_SEQUENCE = 1;
    
    private Map<Long, Ticket> ticketDatabase = new LinkedHashMap<>();

    @Override
    public List<Ticket> getAll() {
        return new ArrayList<>(ticketDatabase.values());
    }

    @Override
    public Ticket get(long id) {
        return ticketDatabase.get(id);
    }

    @Override
    public void add(Ticket ticket) {
        ticket.setId(this.getNextTicketId());
        ticketDatabase.put(ticket.getId(), ticket);
    }

    @Override
    public void update(Ticket ticket) {
        ticketDatabase.put(ticket.getId(), ticket);
    }

    private synchronized long getNextTicketId() {
        return this.TICKET_ID_SEQUENCE++;
    }
}
