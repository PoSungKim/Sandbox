package biz.market.domain;

import biz.customer.domain.Customer;
import biz.ticket.meta.TITLE;
import biz.ticket.TicketManager;
import biz.ticket.TicketStore;
import fw.annotation.BizObject;
import fw.util.ApplicationLogger;

import java.util.ArrayList;
import java.util.List;

@BizObject
public class Market {
    // Market is singleton, which takes a role of a single point of access to the instance of other code
    private static final Market  instance      = new Market();
    private final TicketManager  ticketManager = new TicketManager(1L, "자비스", TITLE.MANAGER);
    // TicketStore is also singleton; however, it is not defined as a static variable
    // The purpose is to maintain the relationship in which TicketStore can be accessed only through Market
    private final TicketStore    ticketStore   = new TicketStore(1L, "Airline Ticket Store");
    private final List<Customer> customerList  = new ArrayList<>(){{
        this.add(new Customer(0L, "이하은"));
        this.add(new Customer(1L, "김보성"));
        this.add(new Customer(2L, "몽이"));
        this.add(new Customer(3L, "퐁이"));
        this.add(new Customer(4L, "오렌지 고양이 레아"));
    }};
    private Market() {
        // prohibit instantiation by others
    }
    public static Market getInstance() {
        return instance;
    }
    public TicketStore getTicketStore() {
        return ticketStore;
    }
    public List<Customer> getcustomerList() {
        return customerList;
    }
    public void open() {
        ApplicationLogger.log(Thread.currentThread(), "BIZ", this, "open");

        for(int i = 0; i < 10; i++) ticketManager.makeTicket();
        ticketManager.checkForOpenTicket();
    }
    public void run() {
        ApplicationLogger.log(Thread.currentThread(), "BIZ", this, "running");

        // 순차 처리
        //for(Customer customer : customerList) {
        //    customer.purchaseV1();
        //}

        // 병렬 처리
        for(Customer customer : customerList) {
            Thread customerThread = new Thread(customer);
            // default : false; true --> main 쓰레드가 종료되면 daemon 쓰레드도 모두 종료된다
//            customerThread.setDaemon(true);

            customerThread.start();
//            try {
//                // main 쓰레드는 customerThread.start() 연산이 끝날 때까지 대기한다
//                // synchronous-blocking하게 작동한다
//                // for문마다 새로운 Thread가 생성되기 때문에 멀티쓰레드이지만, 병렬처리는 안되고, 순차처리가 된다는 의미이다
//                // main 쓰레드가 customerThread.start() 연산이 끝날 때까지 대기하지 않고, 바로 바로 nonblocking하게 작동하게 하기 위해서는 .join()를 사용하면 안된다
//                customerThread.join();
//
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        }

        Market.getInstance().stopMarket();
    }

    public void stopMarket() {
        synchronized (Market.getInstance()) {
            try {
                System.out.println("WAIT!");
                Market.getInstance().wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void finishMarket() {
        synchronized (Market.getInstance()) {
            System.out.println("NOTIFYALL!");
            Market.getInstance().notifyAll();
        }
    }

    public void close() {
        ApplicationLogger.log(Thread.currentThread(), "BIZ", this, "closed");

        ticketManager.checkForOpenTicket();
    }
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
//        return String.format("%s(ticketManager : %s, ticketStore : %s, customerList : %s)", this.getClass().getSimpleName(), this.ticketManager, this.ticketStore, this.customerList);
    }
}