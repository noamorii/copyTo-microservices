package fel.cvut.order.service;

import fel.cvut.order.dao.CategoryDao;
import fel.cvut.order.dao.OrderDao;
import fel.cvut.order.exception.ValidationException;
import fel.cvut.order.model.Category;
import fel.cvut.order.model.Order;
import fel.cvut.order.model.OrderState;
import fel.cvut.order.rest.requests.CreateOrderRequest;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderDao orderDao;
    private final CategoryDao categoryDao;
    private final RestTemplate restTemplate;

    public Order createOrder(CreateOrderRequest request) {
        Objects.requireNonNull(request);
        if (new Date().compareTo(request.deadline()) > 0){
            throw new ValidationException("The deadline in the request is set for the past");
        }
        Order order = Order.newBuilder()
                .addClientId(request.userId())
                .addLink(request.link())
                .addDeadline(request.deadline())
                .addPrice(request.price())
                .addInsertionDate(LocalDate.now())
                .setOrderState(OrderState.ADDED)
                .build();
        orderDao.save(order);
        return order;
    }

    @CachePut(value = "orders", key = "#order")
    public void update(Order order) {
        Objects.requireNonNull(order);
        orderDao.save(order);
    }

    @CacheEvict(value = "orders", key = "#order")
    public void delete(Order order) {
        Objects.requireNonNull(order);
        List<Category> categories = order.getCategories();
        categories.forEach(category -> {
            category.removeOrder(order);
            categoryDao.save(category);
        });
        orderDao.delete(order);
    }

    public void addCategory(Order order, Category category) {
        Objects.requireNonNull(order);
        Objects.requireNonNull(category);

        order.addCategory(category);
        category.addOrder(order);

        orderDao.save(order);
        categoryDao.save(category);
    }

    public void removeCategory(Order order, Category category) {
        Objects.requireNonNull(order);
        Objects.requireNonNull(category);

        order.removeCategory(category);
        category.removeOrder(order);

        orderDao.save(order);
        categoryDao.save(category);
    }

    public List<Order> findAllOrders() {
        return orderDao.findAll();
    }

    @Cacheable(value = "orders", key = "#id")
    public Order findById(Integer id) {
        return orderDao.findById(id).orElse(null);
    }

    public List<Order> findOrdersByCategory(Category category) {
       return category.getOrders();
    }
}
