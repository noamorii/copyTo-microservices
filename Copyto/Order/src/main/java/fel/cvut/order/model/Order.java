package fel.cvut.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "system_orders")
public class Order extends AbstractEntity {

    private double price;
    private LocalDate insertionDate;
    private Date deadline;
    private String link;
    private boolean isOpen;

    private Integer assigneeId;
    private Integer clientId;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @ManyToMany
    @OrderBy("name")
    private List<Category> categories = new ArrayList<>();

    public void addCategory(Category category){
        categories.add(category);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
    }
}
