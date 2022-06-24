package fel.cvut.order.service;

import fel.cvut.order.dao.CategoryDao;
import fel.cvut.order.dao.OrderDao;
import fel.cvut.order.exception.NotFoundException;
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

/**
 * Represents an Order Service.
 */
@Service
@AllArgsConstructor
public class OrderService {

    private final OrderDao orderDao;
    private final CategoryDao categoryDao;
    private final RestTemplate restTemplate;

    /**
     * Creates new order
     * @param request CreateOrderRequest which contains the data for creating a new order
     * @return Order which been created
     */
    public Order createOrder(CreateOrderRequest request) {
        Objects.requireNonNull(request);
        if (new Date().compareTo(request.getDeadline()) > 0){
            throw new ValidationException("The deadline in the request is set for the past");
        }
        Order order = Order.newBuilder()
                .addClientId(request.getUserId())
                .addLink(request.getLink())
                .addDeadline(request.getDeadline())
                .addPrice(request.getPrice())
                .addInsertionDate(LocalDate.now())
                .setOrderState(OrderState.ADDED)
                .setIsOpen(true)
                .build();
        orderDao.save(order);
        return order;
    }

    /**
     * Updates the order state
     * @param order The Order whose data need to be updated
     */
    @CachePut(value = "orders", key = "#order")
    public void update(Order order) {
        Objects.requireNonNull(order);
        orderDao.save(order);
    }

    /**
     * Deletes the order
     * @param order Order to be deleted
     */
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

    /**
     * Adds a category to the order and the order to the category
     * @param order An Order that will accept a category
     * @param category Category that will be added to the order
     */
    public void addCategory(Order order, Category category) {
        Objects.requireNonNull(order);
        Objects.requireNonNull(category);

        order.addCategory(category);
        category.addOrder(order);

        orderDao.save(order);
        categoryDao.save(category);
    }

    /**
     * Removes a category from the order and the order from the category
     * @param order An Order that will remove a category
     * @param category Category that will be removed from the order
     */
    public void removeCategory(Order order, Category category) {
        Objects.requireNonNull(order);
        Objects.requireNonNull(category);

        order.removeCategory(category);
        category.removeOrder(order);

        orderDao.save(order);
        categoryDao.save(category);
    }

    /**
     * Returns a list of all orders
     * @return List of all orders
     */
    public List<Order> findAllOrders() {
        return orderDao.findAll();
    }

    /**
     * Returns Order with that id
     * @param id Integer identifier by which the order is searched for
     * @return An Order with this identifier
     */
    @Cacheable(value = "orders", key = "#id")
    public Order findById(Integer id) {
        Order order = orderDao.findById(id).orElse(null);
        if (order == null)
            throw new NotFoundException("Order with id=" + id + " not found");
        return order;
    }

    /**
     * Returns List of Orders which have this Category
     * @param category Category by which orders are searched for
     * @return List of Orders
     */
    public List<Order> findOrdersByCategory(Category category) {
       return category.getOrders();
    }
}
