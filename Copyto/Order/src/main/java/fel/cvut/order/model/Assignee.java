package fel.cvut.order.model;

/**
 * Represents Assignee person to the order
 */
public class Assignee {

    private Workplace workplace;
    private Version version;

    public Assignee(Workplace workplace) {
        this.workplace = workplace;
    }

    /**
     * Creates backup ot the workplace
     */
    public void makeBackup() {
        version = workplace.createSnapshot();
    }

    /**
     * Restores old version
     */
    public void undo() {
        if (version != null) {
            this.workplace = version.restore();
        }
    }
}
