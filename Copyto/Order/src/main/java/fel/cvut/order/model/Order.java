package fel.cvut.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "system_orders")
public class Order extends AbstractEntity {

    private double price;
    private Date insertionDate;
    private Date deadline;
    private String link;
    private OrderState orderState;
}
