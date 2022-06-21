package fel.cvut.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "system_categories")
public class Category extends AbstractEntity {

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    @OrderBy("insertionDate")
    private List<Order> orders = new ArrayList<>();

    private String name;

    public void addOrder(Order order){
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }
}
