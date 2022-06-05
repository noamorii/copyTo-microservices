package fel.cvut.order.rest.requests;

import fel.cvut.order.model.OrderState;

import java.util.Date;

public record CreateOrderRequest(
        Date deadline,
        double price,
        String link
) {
}
