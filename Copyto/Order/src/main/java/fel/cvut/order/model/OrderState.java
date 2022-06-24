package fel.cvut.order.model;

/**
 * Represents enum of order and design pattern State Machine
 */
public enum OrderState {
    ADDED("ADDED"){
        @Override
        boolean isEditable() {
            return true;
        }
    },
    IN_PROCESS("IN_PROCESS") {
        @Override
        boolean isEditable() {
            return true;
        }
    },
    DONE("DONE"){
        @Override
        boolean isEditable() {
            return false;
        }
    };

    private final String name;

    OrderState(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    abstract boolean isEditable();
}
