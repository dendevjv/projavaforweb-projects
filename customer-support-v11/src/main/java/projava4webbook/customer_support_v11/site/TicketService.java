package projava4webbook.customer_support_v11.site;

import java.util.List;

public interface TicketService {
    List<Ticket> getAllTickets();
    Ticket getTicket(long ticketId);
    void save(Ticket ticket);
}
