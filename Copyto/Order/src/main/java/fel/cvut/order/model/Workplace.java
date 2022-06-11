package fel.cvut.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "system_workplaces")
public class Workplace extends AbstractEntity {

    private boolean isEditable;
    private Integer currentVersionId;

    @OneToMany
    @OrderBy("createdAt")
    List<Version> versions = new ArrayList<>();
}
