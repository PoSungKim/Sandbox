package biz.ticket;

import biz.market.domain.Market;
import biz.ticket.domain.Ticket;
import biz.ticket.meta.STATUS;
import biz.ticket.meta.TITLE;

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
        Long cnt = 0L;
        synchronized (Market.getInstance().getTicketStore()) {
            for (Ticket ticket : Market.getInstance().getTicketStore().getTickets()) {
                if (ticket.getStatus().equals(STATUS.OPEN)) {
                    cnt++;
                }
            }
        }
        System.out.printf("%s %s %s open!\n", cnt, cnt > 0 ? "tickets" : "ticket", cnt > 0 ? "are" : "is");
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