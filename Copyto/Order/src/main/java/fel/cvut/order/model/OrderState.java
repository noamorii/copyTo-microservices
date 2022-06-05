package fel.cvut.order.model;

public enum OrderState {
    ADDED("ADDED"),
    IN_PROCESS("IN_PROGRESS"),
    DONE("DONE");

    private final String name;

    OrderState(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
