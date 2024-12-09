package biz.customer.domain;

import biz.market.domain.Market;
import biz.ticket.Person;
import biz.ticket.TicketStore;
import biz.ticket.domain.Ticket;
import biz.ticket.meta.STATUS;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Customer extends Person implements Runnable {
    public Customer(Long id, String name) {
        super(id, name);
    }
    public void purchaseV1() {
        System.out.printf("\n%s is looking for an open ticket\n", this);
        for(Ticket ticket : Market.getInstance().getTicketStore().getTickets()) {
            if (ticket.getStatus().equals(STATUS.OPEN)){
                System.out.printf("%s has found and is purchasing %s\n", this, ticket);
                ticket.purchased(this);
                break;
            }
        }
    }
    public void purchaseV2() {
        System.out.printf("%s %s is looking for an open ticket\n", this.getClass().getSimpleName(), this.getName());

        for(Ticket ticket : Market.getInstance().getTicketStore().getTickets()) {
            synchronized (ticket) {
                if (ticket.getStatus().equals(STATUS.OPEN)){
                    System.out.printf("%s %s has found and is purchasing %s\n", this.getClass().getSimpleName(), this.getName(), ticket);
                    ticket.purchased(this);
                    break;
                }
            }
        }
    }
    public void purchaseV3() {
        System.out.printf("%s %s is looking for an open ticket\n", this.getClass().getSimpleName(), this.getName());

        synchronized (Market.getInstance().getTicketStore()) {
            for(Ticket ticket : Market.getInstance().getTicketStore().getTickets()) {
                if (ticket.getStatus().equals(STATUS.OPEN)){
                    System.out.printf("%s %s has found and is purchasing %s\n", this.getClass().getSimpleName(), this.getName(), ticket);
                    ticket.purchased(this);
                    break;
                }
            }
        }
    }

    @Override
    public void run() {
        System.out.printf("\n[%s-current thread(%s)] BIZ : %s is running!\n", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")), Thread.currentThread().getName(), this);
        this.purchaseV3();
    }

    @Override
    public String toString() {
        return String.format("%s(id : %s, name : %s)", this.getClass().getSimpleName(), super.getId(), super.getName());
    }
}