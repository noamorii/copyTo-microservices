package fel.cvut.order.rest.requests;

import java.util.Date;

public record CreateOrderRequest(
        Date deadline,
        double price,
        String link
) {
}
