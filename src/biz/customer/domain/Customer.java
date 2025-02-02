package biz.customer.domain;

import biz.market.domain.Market;
import biz.ticket.Person;
import biz.ticket.TicketStore;
import biz.ticket.domain.Ticket;
import biz.ticket.meta.STATUS;
import fw.util.ApplicationLogger;

import java.math.BigDecimal;
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

        // (intrinsic lock) within the synchronized block, only a single Customer thread can occupy TicketStore at a time
        // since Tickets can be accessed only through TicketStore, only the thread which takes TicketStore first can also take Tickets
        synchronized (Market.getInstance().getTicketStore()) {
            for(Ticket ticket : Market.getInstance().getTicketStore().getTickets()) {
                System.out.println(this + "> current searching ticket : " + ticket);
                if (ticket.getStatus().equals(STATUS.OPEN)){
                    System.out.printf("%s %s has found and is purchasing %s\n", this.getClass().getSimpleName(), this.getName(), ticket);
                    sleep();
                    ticket.purchased(this);
                    break;
                }
            }
        }
    }
    public void purchaseV3() {
        System.out.printf("%s %s is looking for an open ticket\n", this.getClass().getSimpleName(), this.getName());

        for(Ticket ticket : Market.getInstance().getTicketStore().getTickets()) {
            System.out.println(this + "> current searching ticket : " + ticket);

            // (intrinsic lock) within the synchronized block, only a single Customer thread can occupy Ticket at a time
            synchronized (ticket) {
                if (ticket.getStatus().equals(STATUS.OPEN)){
                    System.out.printf("%s %s has found and is purchasing %s\n", this.getClass().getSimpleName(), this.getName(), ticket);
                    sleep();
                    ticket.purchased(this);
                    break;
                }
            }
        }
    }

    public void purchaseV4() {
        for(Ticket ticket : Market.getInstance().getTicketStore().getTickets()) {
            System.out.println(this + "> current searching ticket : " + ticket);
            if (ticket.getStatus().equals(STATUS.OPEN)){
                synchronized(ticket) {
                    if (ticket.getStatus().equals(STATUS.CLOSE)) {
                        continue;
                    }
                    System.out.printf("%s %s has found and is purchasing %s\n", this.getClass().getSimpleName(), this.getName(), ticket);
                    sleep();
                    ticket.purchased(this);
                    break;
                }
            }
        }
    }

    private void sleep() {
        try {
            long sleepTime = BigDecimal.valueOf(Math.random()).multiply(new BigDecimal("1000")).longValue();
            System.out.println("SLEEP : " + sleepTime);
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        ApplicationLogger.log(Thread.currentThread(), "BIZ", this, "running");
        this.purchaseV4();

        if (Market.getInstance().getTicketManager().isFinalTicketPurchased()) {
            Market.getInstance().closeMarket();
        }
    }

    @Override
    public String toString() {
        return String.format("%s(id : %s, name : %s)", this.getClass().getSimpleName(), super.getId(), super.getName());
    }
}