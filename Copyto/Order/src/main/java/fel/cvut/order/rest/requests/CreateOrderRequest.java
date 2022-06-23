package fel.cvut.order.rest.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public final class CreateOrderRequest {

    private Integer userId;
    @JsonProperty("deadline")
    private Date deadline;
    @JsonProperty("price")
    private double price;
    @JsonProperty("link")
    private String link;

    public CreateOrderRequest(
            Date deadline,
            double price,
            String link
    ) {
        this.userId = null;
        this.deadline = deadline;
        this.price = price;
        this.link = link;
    }
}
