package projava4webbook.customer_support_v13.site;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

@Validated
public interface TicketService {
    @NotNull
    List<Ticket> getAllTickets();
    
    Ticket getTicket(
            @Min(value = 1L, message = "{validate.ticketService.getTicket.id}")
            long ticketId
    );
    
    void save(
            @NotNull(message = "{validate.ticketService.save.ticket}")
            @Valid Ticket ticket
    );
}
