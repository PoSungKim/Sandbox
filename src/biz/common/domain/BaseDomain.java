package biz.common.domain;

public class BaseDomain {
    private final Long id;
    private final String name;

    public BaseDomain(Long id, String name) {
        this.id = id;
        this.name = name;
        this.greet();
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void greet() {
        System.out.printf("Hello, this is %s %s\n", this.getClass().getSimpleName(), this.getName());
    }
}