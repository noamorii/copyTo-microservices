package fel.cvut.order.rest.requests;

import java.util.Date;

public record CreateOrderRequest(
        Integer userId,
        Date deadline,
        double price,
        String link
) {
}
