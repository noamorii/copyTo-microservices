package fel.cvut.order.rest.controllers;

import fel.cvut.order.rest.requests.CreateOrderRequest;
import fel.cvut.order.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public void createOrder(@RequestBody CreateOrderRequest request) {
        log.info("new order creating {}", request);
        orderService.createOrder(request);
    }
}
