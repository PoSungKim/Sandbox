package biz.ticket.domain;

import biz.common.domain.BaseDomain;
import biz.customer.domain.Customer;
import biz.ticket.meta.STATUS;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ticket extends BaseDomain {
    private STATUS status;
    private Customer owner;
    public Ticket(Long id, String name, STATUS status) {
        super(id, name);
        this.status = status;
    }
    public STATUS getStatus() {
        return this.status;
    }
    public synchronized void purchased(Customer owner) {
        System.out.printf("[%s-current thread(%s)] BIZ : %s is purchased!\n", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")), Thread.currentThread().getName(), this);
        this.owner = owner;
        this.status = STATUS.CLOSE;
        System.out.printf("%s is now close\n\n", this);
    }

    @Override
    public String toString() {
        return String.format("%s(id : %s, name : %s, status : %s, owner : %s)", this.getClass().getSimpleName(), super.getId(), super.getName(), this.status, this.owner);
    }
}
