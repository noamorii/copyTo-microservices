package fel.cvut.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "system_categories")
public class Category extends AbstractEntity {

    @ManyToMany(mappedBy = "categories")
    @OrderBy("insertionDate")
    private List<Order> orders = new ArrayList<>();

    private String name;
}
