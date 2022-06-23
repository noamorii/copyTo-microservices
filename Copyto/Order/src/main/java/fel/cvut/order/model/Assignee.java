package fel.cvut.order.model;

public class Assignee {

    private Workplace workplace;
    private Version version;

    public Assignee(Workplace workplace) {
        this.workplace = workplace;
    }

    public void makeBackup() {
        version = workplace.createSnapshot();
    }

    public void undo() {
        if (version != null) {
            this.workplace = version.restore();
        }
    }
}
