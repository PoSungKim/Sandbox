package biz.ticket.meta;

public enum TITLE {
    MANAGER("Manager");

    private final String name;

    TITLE(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
