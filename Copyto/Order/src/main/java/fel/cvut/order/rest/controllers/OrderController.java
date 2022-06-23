package fel.cvut.order.rest.controllers;

import fel.cvut.order.exception.NotFoundException;
import fel.cvut.order.model.Candidate;
import fel.cvut.order.model.Order;
import fel.cvut.order.model.OrderState;
import fel.cvut.order.rest.requests.CreateOrderRequest;
import fel.cvut.order.rest.requests.OrderResponse;
import fel.cvut.order.rest.util.RestUtils;
import fel.cvut.order.service.CandidateService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final CategoryService categoryService;
    private final CandidateService candidateService;
    private final RestTemplate restTemplate;

    @PostMapping(value = "user/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createOrder(HttpServletRequest request, @RequestBody CreateOrderRequest orderRequest) {
        int id = RestUtils.getCookieUserId(request.getCookies());
        if (id == 0)
            throw new NotFoundException("You aren't logged in");

        orderRequest.setUserId(id);
        log.info("new order creating {}", orderRequest);
        Order order = orderService.createOrder(orderRequest);

        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/order/{id}", order.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.findAllOrders().stream()
            .map(order -> new OrderResponse(
                order.getId(),
                order.getInsertionDate(),
                order.getAssigneeId(),
                order.getClientId()))
            .collect(Collectors.toList());
    }


    @GetMapping(value = "/order/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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
        log.info("Order {} was deleted.", order);
    }

    @GetMapping(value = "/order/{id}/candidate")
    public void becomeCandidate(@PathVariable Integer id) {
        Order order = orderService.findById(id);
        if (order.getOrderState() != OrderState.ADDED)
            throw new IllegalArgumentException("Order doesn't accept new candidates");

        Candidate candidate = candidateService.findById(id);
        candidateService.createCandidate(order, candidate);
        log.info("Candidate {} was added to order {}", candidate, order);
    }

    @GetMapping(value = "/order/{orderId}/candidate/{candidateId}")
    public void acceptCandidate(@PathVariable Integer orderId, @PathVariable Integer candidateId ) {
        Order order = orderService.findById(orderId);
        Candidate candidate = candidateService.findById(candidateId);

        candidateService.acceptCandidate(candidate, order);
        log.info("Candidate {} was added to order {}", candidate, order);
    }
}