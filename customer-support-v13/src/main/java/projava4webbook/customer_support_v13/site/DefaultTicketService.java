package projava4webbook.customer_support_v13.site;

import java.time.Instant;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

@Service
public class DefaultTicketService implements TicketService {
    @Inject
    TicketRepository ticketRepository;

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.getAll();
    }

    @Override
    public Ticket getTicket(long ticketId) {
        return ticketRepository.get(ticketId);
    }

    @Override
    public void save(Ticket ticket) {
        if (ticket.getId() < 1) {
            ticket.setDateCreated(Instant.now());
            ticketRepository.add(ticket);
        } else {
            ticketRepository.update(ticket);
        }
    }

}
