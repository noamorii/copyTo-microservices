package fel.cvut.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;

/**
 * Represents Version of Workplace and Design Pattern Memento
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "system_versions")
public class Version extends AbstractEntity {

    @Transient
    private Workplace workplace;
    private boolean isEditable;
    private Integer currentVersionId;
    private String title;
    private String text;
    private LocalDateTime createdAt;
    private static int counter;

    public Version(Workplace workplace, boolean isEditable, String title, String text, LocalDateTime createdAt) {
        this.workplace = workplace;
        this.isEditable = isEditable;
        this.title = title;
        this.text = text;
        this.createdAt = createdAt;
        counter++;
        this.currentVersionId = counter;
    }

    /**
     * Restore version of the workplace
     * @return Workplace, the old version
     */
    public Workplace restore(){
        workplace.setEditable(isEditable);
        workplace.setId(currentVersionId);
        workplace.setTitle(title);
        workplace.setText(text);
        workplace.setCreatedAt(createdAt);
        return workplace;
    }
}
