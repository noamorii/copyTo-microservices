package fel.cvut.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "system_categories")
@Document(indexName = "category")
@Setting(settingPath = "static/es-settings.json")
public class Category extends AbstractEntity {

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    @OrderBy("insertionDate")
    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Order> orders = new ArrayList<>();

    @Field(type = FieldType.Text)
    private String name;

    public void addOrder(Order order){
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }
}
