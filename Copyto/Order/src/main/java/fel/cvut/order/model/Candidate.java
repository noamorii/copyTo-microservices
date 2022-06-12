package fel.cvut.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "system_candidates")
public class Candidate extends AbstractEntity {
    private Integer userId;
    private Integer orderId;
    private LocalDateTime registeredToOrderAt;
}