package biz.market.domain;

import biz.customer.domain.Customer;
import biz.ticket.meta.TITLE;
import biz.ticket.TicketManager;
import biz.ticket.TicketStore;
import fw.annotation.BizObject;

import java.util.ArrayList;
import java.util.List;

@BizObject
public class Market {
    private static final Market instance = new Market();
    private final TicketManager ticketManager = new TicketManager(1L, "자비스", TITLE.MANAGER);
    private final TicketStore ticketStore = new TicketStore(1L, "Airline Ticket Store");
    private final List<Customer> customerList = new ArrayList<>(){{
        this.add(new Customer(1L, "이하은"));
        this.add(new Customer(2L, "김보성"));
    }};
    private Market() {
    }
    public static Market getInstance() {
        return instance;
    }
    public void open() {
        System.out.println("\n==========================================");
        System.out.printf("BIZ : %s is open!", this.getClass().getSimpleName());
        System.out.println("\n==========================================");

        for(int i = 0; i < 10; i++) ticketManager.makeTicket(ticketStore);
        ticketManager.checkForOpenTicket(ticketStore);
    }
    public void run() {
        System.out.println("\n==========================================");
        System.out.printf("BIZ : %s is running!", this.getClass().getSimpleName());
        System.out.println("\n==========================================");
        for(Customer customer : customerList) {
            customer.purchase(ticketStore);
        }
    }
    public void close() {
        System.out.println("\n==========================================");
        System.out.printf("BIZ : %s is close!", this.getClass().getSimpleName());
        System.out.println("\n==========================================");
        ticketManager.checkForOpenTicket(ticketStore);
    }
    @Override
    public String toString() {
        return String.format("%s(ticketManager : %s, ticketStore : %s, customerList : %s)", this.getClass().getSimpleName(), this.ticketManager, this.ticketStore, this.customerList);
    }
}