package projava4webbook.customer_support_v12.site;

import java.util.List;

public interface TicketService {
    List<Ticket> getAllTickets();
    Ticket getTicket(long ticketId);
    void save(Ticket ticket);
}
