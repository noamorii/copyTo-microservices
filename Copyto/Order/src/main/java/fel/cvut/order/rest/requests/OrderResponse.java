package fel.cvut.order.rest.requests;

import java.time.LocalDate;
import java.util.Date;

public record OrderResponse(
        Integer id,
        LocalDate insertionDate,
        Integer assigneeId,
        Integer clientId
) {
}
