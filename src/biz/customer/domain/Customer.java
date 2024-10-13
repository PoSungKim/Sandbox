package biz.customer.domain;

import biz.ticket.Person;
import biz.ticket.TicketStore;
import biz.ticket.domain.Ticket;
import biz.ticket.meta.STATUS;

public class Customer extends Person {
    public Customer(Long id, String name) {
        super(id, name);
    }
    public void purchase(TicketStore ticketStore) {
        System.out.printf("\n%s %s is looking for an open ticket\n", this.getClass().getSimpleName(), this.getName());
        for(Ticket ticket : ticketStore.getTickets()) {
            if (ticket.getStatus().equals(STATUS.OPEN)){
                System.out.printf("%s %s found and is purchasing %s\n", this.getClass().getSimpleName(), this.getName(), ticket);
                ticket.purchased(this.getName());
                break;
            }
        }
    }
}