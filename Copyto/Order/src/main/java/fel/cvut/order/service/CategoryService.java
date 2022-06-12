package fel.cvut.order.service;

import fel.cvut.order.dao.CategoryDao;
import fel.cvut.order.dao.OrderDao;
import fel.cvut.order.model.Category;
import fel.cvut.order.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public record CategoryService(
        CategoryDao categoryDao,
        OrderDao orderDao) {

    public void createCategory(Category category) {
        Objects.requireNonNull(category);
        categoryDao.save(category);
    }

    public void addOrder(Category category, Order order) {
        Objects.requireNonNull(category);
        Objects.requireNonNull(order);

        category.addOrder(order);
        order.addCategory(category);
        orderDao.save(order);
    }

    public void removeOrder(Category category, Order order){
        Objects.requireNonNull(category);
        Objects.requireNonNull(order);

        order.removeCategory(category);
        category.removeOrder(order);
        orderDao.save(order);
        categoryDao.save(category);
    }

    public void updateCategoryName(Category category, String name) {
        Objects.requireNonNull(category);
        Objects.requireNonNull(name);

        category.setName(name);
        categoryDao.save(category);
    }

    public void deleteCategory(Category category) {
        Objects.requireNonNull(category);
        category.getOrders().forEach(order -> {
            order.removeCategory(category);
            orderDao.save(order);
        });
        categoryDao.delete(category);
    }

    public List<Category> findAllCategories() {
        return categoryDao.findAll();
    }

    public Category findById(Integer id)  {
        return categoryDao.findById(id).orElse(null);
    }
}