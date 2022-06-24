package fel.cvut.order.service;

import fel.cvut.order.dao.CategoryDao;
import fel.cvut.order.dao.OrderDao;
import fel.cvut.order.model.Category;
import fel.cvut.order.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Represents a Category Service.
 */
@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryDao categoryDao;
    private final OrderDao orderDao;

    /**
     * Creates new category
     * @param category Category to be saved
     */
    public void createCategory(Category category) {
        Objects.requireNonNull(category);
        categoryDao.save(category);
    }

    /**
     * Adds an order to the category and the category to the order
     * @param category A Category that will accept an order
     * @param order An Order that will be added to the category
     */
    public void addOrder(Category category, Order order) {
        Objects.requireNonNull(category);
        Objects.requireNonNull(order);

        category.addOrder(order);
        order.addCategory(category);
        orderDao.save(order);
        categoryDao.save(category);
    }

    /**
     * Removes an order from the category and the category from the order
     * @param category A Category that will remove an order
     * @param order An Order that will be removed from the category
     */
    public void removeOrder(Category category, Order order){
        Objects.requireNonNull(category);
        Objects.requireNonNull(order);

        order.removeCategory(category);
        category.removeOrder(order);
        orderDao.save(order);
        categoryDao.save(category);
    }

    /**
     * Updates category name
     * @param category A Category which receives new name
     * @param name A String with new name
     */
    public void updateCategoryName(Category category, String name) {
        Objects.requireNonNull(category);
        Objects.requireNonNull(name);

        category.setName(name);
        categoryDao.save(category);
    }

    /**
     *  Deletes the category and removes that category from all orders which have that category
     * @param category Category to be removed
     */
    public void deleteCategory(Category category) {
        Objects.requireNonNull(category);
        category.getOrders().forEach(order -> {
            order.removeCategory(category);
            orderDao.save(order);
        });
        categoryDao.delete(category);
    }

    /**
     * Returns a list of all categories
     * @return List of Categories
     */
    public List<Category> findAllCategories() {
        return categoryDao.findAll();
    }

    /**
     * Returns Category with that id
     * @param id Integer identifier by which the category is searched for
     * @return A Category with this identifier
     */
    public Category findById(Integer id)  {
        return categoryDao.findById(id).orElse(null);
    }

    /**
     * Returns List of Orders which have this Order
     * @param order Order by which categories are searched for
     * @return List of Categories
     */
    public List<Category> findCategoriesByOrder(Order order) {
        return order.getCategories();
    }
}