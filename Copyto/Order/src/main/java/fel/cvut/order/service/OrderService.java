package fel.cvut.order.service;

import fel.cvut.order.dao.OrderDao;
import fel.cvut.order.model.Order;
import fel.cvut.order.model.OrderState;
import fel.cvut.order.rest.requests.CreateOrderRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderDao orderDao;

    public void createOrder(CreateOrderRequest request) {
        Order order = Order.builder()
                .link(request.link())
                .deadline(request.deadline())
                .price(request.price())
                .insertionDate(LocalDate.now())
                .orderState(OrderState.ADDED)
                .build();
        orderDao.save(order);
    }
}
