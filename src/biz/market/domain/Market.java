package biz.market.domain;

import biz.customer.domain.Customer;
import biz.ticket.meta.TITLE;
import biz.ticket.TicketManager;
import biz.ticket.TicketStore;
import fw.annotation.BizObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@BizObject
public class Market {
    private static final Market  instance      = new Market();
    private final TicketManager  ticketManager = new TicketManager(1L, "자비스", TITLE.MANAGER);
    private final TicketStore    ticketStore   = new TicketStore(1L, "Airline Ticket Store");
    private final List<Customer> customerList  = new ArrayList<>(){{
        this.add(new Customer(0L, "이하은"));
        this.add(new Customer(1L, "김보성"));
        this.add(new Customer(2L, "몽이"));
        this.add(new Customer(3L, "퐁이"));
    }};
    private Market() {
        // prohibit instantiation by others
    }
    public static Market getInstance() {
        return instance;
    }
    public TicketStore getTicketStore() {
        return this.ticketStore;
    }
    public void open() {
        System.out.println("\n====================================================================================");
        System.out.printf("[%s-current thread(%s)] BIZ : %s is open!", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")), Thread.currentThread().getName(), this.getClass().getSimpleName());
        System.out.println("\n====================================================================================");

        for(int i = 0; i < 10; i++) ticketManager.makeTicket();
        ticketManager.checkForOpenTicket();
    }
    public void run() {
        System.out.println("\n====================================================================================");
        System.out.printf("[%s-current thread(%s)] BIZ : %s is running!", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")), Thread.currentThread().getName(), this.getClass().getSimpleName());
        System.out.println("\n====================================================================================");

        // 순차 처리
//        for(Customer customer : customerList) {
//            customer.purchaseV1();
//        }

        // 병렬 처리
        for(Customer customer : customerList) {
            Thread customerThread = new Thread(customer);
            customerThread.start();
        }
    }
    public void close() {
        System.out.println("\n====================================================================================");
        System.out.printf("[%s-current thread(%s)] BIZ : %s is close!", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")), Thread.currentThread().getName(), this.getClass().getSimpleName());
        System.out.println("\n====================================================================================");
//        ticketManager.checkForOpenTicket();
        new Thread(ticketManager).start();
    }
    @Override
    public String toString() {
        return String.format("%s(ticketManager : %s, ticketStore : %s, customerList : %s)", this.getClass().getSimpleName(), this.ticketManager, this.ticketStore, this.customerList);
    }
}