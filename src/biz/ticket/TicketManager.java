package biz.ticket;

import biz.market.domain.Market;
import biz.ticket.domain.Ticket;
import biz.ticket.meta.STATUS;
import biz.ticket.meta.TITLE;

import java.util.stream.Collectors;

public class TicketManager extends Person implements Runnable {
    private final TITLE title;
    public TicketManager(Long id, String name, TITLE title) {
        super(id, name);
        this.title = title;
    }
    public TITLE getTitle() {
        return title;
    }
    public void makeTicket() {
        Market.getInstance().getTicketStore().addTicket();
    }
    public void checkForOpenTicket() {
        System.out.printf("%s %s is checking for open tickets!%n", this.getTitle(), this.getName());
        Long cnt = countOpenTicket();

        System.out.printf("%s %s %s open!\n", cnt, cnt > 0 ? "tickets" : "ticket", cnt > 0 ? "are" : "is");

        Market.getInstance().getTicketStore().getTickets()
                .forEach(System.out::println);
    }
    public Long countOpenTicket() {
        return (long) Market.getInstance().getTicketStore().getTickets().stream().filter(ticket -> ticket.getStatus().equals(STATUS.OPEN)).toList().size();
    }
    public Long countCloseTicket() {
        return (long) Market.getInstance().getTicketStore().getTickets().stream().filter(ticket -> ticket.getStatus().equals(STATUS.CLOSE)).toList().size();
    }
    @Override
    public String toString() {
        return super.getName() + " " + this.getTitle();
    }
    @Override
    public void run() {
        checkForOpenTicket();
    }
}