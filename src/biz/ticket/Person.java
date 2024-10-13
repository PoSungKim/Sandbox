package biz.ticket;

import biz.common.domain.BaseDomain;

public class Person extends BaseDomain {
    private final String name;
    public String getName() {
        return name;
    }
    public Person(Long id, String name) {
        super(id, name);
        this.name = name;
        this.greet();
    }
    public void greet() {
        System.out.printf("Hello, this is %s %s\n", this.getClass().getSimpleName(), this.name);
    }
}
