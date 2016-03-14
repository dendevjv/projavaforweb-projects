package projava4webbook.customer_support_v12.site;

import java.util.List;

public interface TicketRepository {
    List<Ticket> getAll();
    Ticket get(long id);
    void add(Ticket ticket);
    void update(Ticket ticket);
}
