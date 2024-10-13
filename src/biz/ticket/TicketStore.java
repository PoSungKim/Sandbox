package biz.ticket;

import biz.common.domain.BaseDomain;
import biz.ticket.domain.Ticket;
import biz.ticket.meta.STATUS;
import java.util.ArrayList;
import java.util.List;

public class TicketStore extends BaseDomain {
    private final List<Ticket> tickets;
    public TicketStore(Long id, String name) {
        super(id, name);
        this.tickets = new ArrayList<>();
    }
    public List<Ticket> getTickets() {
        return tickets;
    }
    public void addTicket() {
        this.tickets.add(new Ticket((long) tickets.size(), "Taiwan", STATUS.OPEN));
    }
}
