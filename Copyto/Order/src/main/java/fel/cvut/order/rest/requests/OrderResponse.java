package fel.cvut.order.rest.requests;

import java.time.LocalDate;

public record OrderResponse(
        Integer id,
        LocalDate insertionDate,
        Integer assigneeId,
        Integer clientId
) {
}
