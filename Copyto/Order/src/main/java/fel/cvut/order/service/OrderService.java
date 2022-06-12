package fel.cvut.order.service;

import fel.cvut.order.dao.CategoryDao;
import fel.cvut.order.dao.OrderDao;
import fel.cvut.order.model.Category;
import fel.cvut.order.model.Order;
import fel.cvut.order.model.OrderState;
import fel.cvut.order.rest.requests.CreateOrderRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public record OrderService(
        OrderDao orderDao,
        CategoryDao categoryDao) {

    public void createOrder(CreateOrderRequest request) {
        Objects.requireNonNull(request);
        Order order = Order.builder()
                .clientId(request.userId())
                .link(request.link())
                .deadline(request.deadline())
                .price(request.price())
                .insertionDate(LocalDate.now())
                .orderState(OrderState.ADDED)
                .build();
        orderDao.save(order);
    }

    public void update(Order order) {
        Objects.requireNonNull(order);
        orderDao.save(order);
    }

    public void remove(Order order) {
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
}
