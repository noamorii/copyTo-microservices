package fel.cvut.order.service;

import fel.cvut.order.dao.CategoryDao;
import fel.cvut.order.dao.OrderDao;
import fel.cvut.order.model.Category;
import fel.cvut.order.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryDao categoryDao;
    @Mock
    private OrderDao orderDao;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private Order order;

    @BeforeEach
    void setUp(){
        List<Order> orders = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        category = new Category();
        order = new Order();

        category.setOrders(orders);
        order.setCategories(categories);
    }

    @Test
    @DisplayName("checks if a category will be created")
    void testCreateCategory() {
        //Act
        categoryService.createCategory(category);

        //Assert
        Mockito.verify(categoryDao, Mockito.times(1)).save(category);
    }

    @Test
    @DisplayName("checks if the order will be added to a category and if the category will have an order")
    void testAddOrder() {
        //Arrange
        int expectedOrdersSize = 1;
        int expectedCategorySize = 1;

        //Act
        categoryService.addOrder(category, order);

        //Assert
        assertEquals(expectedCategorySize, order.getCategories().size());
        assertEquals(expectedOrdersSize, category.getOrders().size());
        Mockito.verify(orderDao,Mockito.times(1)).save(order);
        Mockito.verify(categoryDao, Mockito.times(1)).save(category);
    }

    @Test
    @DisplayName("checks if the order will be removed from a category and if the category will have not this order")
    void testRemoveOrder() {
        //Arrange
        order.addCategory(category);
        category.addOrder(order);
        int expectedOrdersSize = 0;
        int expectedCategorySize = 0;

        //Act
        categoryService.removeOrder(category, order);

        //Assert
        assertEquals(expectedCategorySize, order.getCategories().size());
        assertEquals(expectedOrdersSize, category.getOrders().size());
        Mockito.verify(orderDao, Mockito.times(1)).save(order);
        Mockito.verify(categoryDao, Mockito.times(1)).save(category);
    }

    @Test
    @DisplayName("checks if the category name will be changed")
    void testUpdateCategoryName() {
        //Assert
        String expectedName = "Name for category";

        //Act
        categoryService.updateCategoryName(category, expectedName);

        //Assert
        assertEquals(expectedName, category.getName());
        Mockito.verify(categoryDao, Mockito.times(1)).save(category);
    }

    @Test
    @DisplayName("checks if the category will be removed, and if all orders that had that category will now not have that category")
    void testDeleteCategory() {
        //Assert
        Order order1 = new Order();
        order1.setCategories(new ArrayList<>());
        order1.addCategory(category);
        Order order2 = new Order();
        order2.setCategories(new ArrayList<>());
        order2.addCategory(category);
        category.addOrder(order);
        category.addOrder(order1);
        category.addOrder(order2);

        //Act
        categoryService.deleteCategory(category);

        //Assert
        assertAll(
                ()->assertEquals(0, order.getCategories().size()),
                ()->assertEquals(0, order1.getCategories().size()),
                ()->assertEquals(0, order2.getCategories().size()));
        Mockito.verify(orderDao, Mockito.times(3)).save(order);
        Mockito.verify(categoryDao, Mockito.times(1)).delete(category);
    }

    @Test
    @DisplayName("checks if all categories will be returned")
    void testFindAllCategories() {
        //Act
        categoryService.findAllCategories();

        //Assert
        Mockito.verify(categoryDao, Mockito.times(1)).findAll();

    }

    @Test
    @DisplayName("checks if the category will be found by the id")
    void testFindById() {
        //Arrange
        int id = 1;

        //Act
        categoryService.findById(id);

        //Assert
        Mockito.verify(categoryDao, Mockito.times(1)).findById(id);
    }

    @Test
    @DisplayName("checks if the category will be found by the order")
    void testFindCategoriesByOrder() {
        //Arrange
        List<Category> expectedCategoryList = order.getCategories();

        //Act
        List<Category> actualCategoryList = categoryService.findCategoriesByOrder(order);

        //Assert
        assertEquals(expectedCategoryList, actualCategoryList);
    }
}