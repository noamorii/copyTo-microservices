package fel.cvut.order.rest.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserResponse implements Serializable {
    private final Integer id;
}
