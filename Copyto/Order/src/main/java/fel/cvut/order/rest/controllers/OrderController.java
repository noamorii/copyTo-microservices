package fel.cvut.order.rest.controllers;

import fel.cvut.order.exception.NotFoundException;
import fel.cvut.order.model.Order;
import fel.cvut.order.rest.requests.CreateOrderRequest;
import fel.cvut.order.rest.requests.OrderResponse;
import fel.cvut.order.rest.requests.UserResponse;
import fel.cvut.order.rest.util.RestUtils;
import fel.cvut.order.service.CategoryService;
import fel.cvut.order.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final CategoryService categoryService;
    private final RestTemplate restTemplate;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderRequest request) {
        log.info("new order creating {}", request);
        Order order = orderService.createOrder(request);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", order.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping(value = "/createOrder", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveOrder(@RequestBody final Order request) {
        Order order = orderService.saveOrder(request);
//        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", order.getId());
//        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping
    public List<OrderResponse> getAllOrders() {

        UserResponse currentUser = restTemplate.getForObject(
                "http://USER/api/v1/users/current_user",
                UserResponse.class
        );

        System.out.println("---------!!!!!!!!!!!!!!!!!!!!!!!______________" + currentUser.getId());

        return orderService.findAllOrders().stream()
            .map(order -> new OrderResponse(
                order.getId(),
                order.getInsertionDate(),
                order.getAssigneeId(),
                order.getClientId()))
            .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public Order getOrderById(@PathVariable final Integer id) {
        return orderService.findOrderById(id);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderResponse getById(@PathVariable Integer id) {
        Order order = orderService.findById(id);

        if (order == null)
            throw NotFoundException.create("order", id);

        return new OrderResponse(
            order.getId(),
            order.getInsertionDate(),
            order.getAssigneeId(),
            order.getClientId()
        );
    }

    @GetMapping(value = "/{id}/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrderResponse> getOrdersByCategory(@PathVariable Integer id) {
        List<Order> orders = orderService.findOrdersByCategory(categoryService.findById(id));

        return orders.stream()
            .map(order -> new OrderResponse(
                    order.getId(),
                    order.getInsertionDate(),
                    order.getAssigneeId(),
                    order.getClientId()))
            .collect(Collectors.toList());
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Integer id) {
        final Order order = orderService.findById(id);

        if (order == null)
            throw NotFoundException.create("Order", id);

        orderService.delete(order);
        log.debug("Order {} was deleted.", order);
    }
}