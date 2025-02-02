package biz.ticket.domain;

import biz.common.domain.BaseDomain;
import biz.customer.domain.Customer;
import biz.ticket.meta.STATUS;
import fw.util.ApplicationLogger;

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
    public void purchased(Customer owner) {

        ApplicationLogger.log(Thread.currentThread(), "BIZ", this, "purchased");
        this.owner = owner;
        this.status = STATUS.CLOSE;
        System.out.printf("%s is now closed\n\n", this);
    }

    @Override
    public String toString() {
        return String.format("%s(id : %s, name : %s, status : %s, owner : %s)", this.getClass().getSimpleName(), super.getId(), super.getName(), this.status, this.owner);
    }
}
