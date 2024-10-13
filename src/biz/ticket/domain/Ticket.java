package biz.ticket.domain;

import biz.common.domain.BaseDomain;
import biz.ticket.meta.STATUS;

public class Ticket extends BaseDomain {
    private STATUS status;
    private String owner;
    public Ticket(Long id, String name, STATUS status) {
        super(id, name);
        this.status = status;
    }
    public STATUS getStatus() {
        return this.status;
    }
    public void purchased(String owner) {
        this.owner = owner;
        this.status = STATUS.CLOSE;
        System.out.printf("%s is now close\n", this);
    }
    public String getOwner() {
        return owner;
    }
    @Override
    public String toString() {
        return String.format("%s(id : %s, name : %s, status : %s)", this.getClass().getSimpleName(), super.getId(), super.getName(), this.status);
    }
}
