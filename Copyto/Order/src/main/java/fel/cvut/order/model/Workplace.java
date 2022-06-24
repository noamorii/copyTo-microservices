package fel.cvut.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Represents Workplace and Design Pattern Memento
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "system_workplaces")
public class Workplace extends AbstractEntity {

    private boolean isEditable;
    private Integer currentVersionId;
    private String title;
    private String text;
    private LocalDateTime createdAt;

    /**
     * Creates snapshot of the workplace
     * @return Version
     */
    public Version createSnapshot(){
        return new Version(this, isEditable, currentVersionId, title, text, LocalDateTime.now());
    }
}
