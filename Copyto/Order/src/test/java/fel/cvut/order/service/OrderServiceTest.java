package fel.cvut.order.service;

import fel.cvut.order.dao.CategoryDao;
import fel.cvut.order.dao.OrderDao;
import fel.cvut.order.model.Category;
import fel.cvut.order.model.Order;
import fel.cvut.order.rest.requests.CreateOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderDao orderDao;
    @Mock
    private CategoryDao categoryDao;

    @InjectMocks
    private OrderService orderService;

    private Category category;
    private Order order;

    @BeforeEach
    void setUp() {
        List<Order> orders = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        category = new Category();
        order = new Order();

        category.setOrders(orders);
        order.setCategories(categories);
    }

    @Test
    @DisplayName("checks if an order will be created")
    void testCreateOrder() {
        //Arrange
        Calendar calendar = Calendar.getInstance();
        calendar.set(2099, Calendar.JANUARY, 1, 0, 0, 0);
        Date expectedDeadline = calendar.getTime();
        double expectedPrice = 22;
        String expectedLink = "https://google.com";
        CreateOrderRequest request = new CreateOrderRequest(expectedDeadline, expectedPrice, expectedLink);

        //Act
        Order actualOrder = orderService.createOrder(request);

        //Assert
        assertAll(() -> assertEquals(expectedDeadline, actualOrder.getDeadline()),
                  () -> assertEquals(expectedPrice, actualOrder.getPrice()),
                  () -> assertEquals(expectedLink, actualOrder.getLink()));
        Mockito.verify(orderDao, Mockito.times(1)).save(actualOrder);
    }

    @Test
    @DisplayName("checks if the order will be updated")
    void testUpdate() {
        //Act
        orderService.update(order);

        //Assert
        Mockito.verify(orderDao, Mockito.times(1)).save(order);
    }

    @Test
    @DisplayName("checks if the order will be removed, and if all categories that had that order will now not have that order")
    void testDelete() {
        //Assert
        Category category1 = new Category();
        category1.setOrders(new ArrayList<>());
        category1.addOrder(order);
        Category category2 = new Category();
        category2.setOrders(new ArrayList<>());
        category2.addOrder(order);
        order.addCategory(category);
        order.addCategory(category1);
        order.addCategory(category2);

        //Act
        orderService.delete(order);

        //Assert
        assertAll(
                ()->assertEquals(0, category.getOrders().size()),
                ()->assertEquals(0, category1.getOrders().size()),
                ()->assertEquals(0, category2.getOrders().size()));
        Mockito.verify(categoryDao, Mockito.times(3)).save(category);
        Mockito.verify(orderDao, Mockito.times(1)).delete(order);
    }

    @Test
    @DisplayName("checks if the category will be added to the order and if the order will have the category")
    void testAddCategory() {
        //Arrange
        int expectedOrdersSize = 1;
        int expectedCategorySize = 1;

        //Act
        orderService.addCategory(order,category);

        //Assert
        assertEquals(expectedCategorySize, order.getCategories().size());
        assertEquals(expectedOrdersSize, category.getOrders().size());
        Mockito.verify(orderDao,Mockito.times(1)).save(order);
        Mockito.verify(categoryDao, Mockito.times(1)).save(category);
    }

    @Test
    @DisplayName("checks if the category will be removed from the order and if the order will have not this category")
    void testRemoveCategory() {
        //Arrange
        order.addCategory(category);
        category.addOrder(order);
        int expectedOrdersSize = 0;
        int expectedCategorySize = 0;

        //Act
        orderService.removeCategory(order, category);

        //Assert
        assertEquals(expectedCategorySize, order.getCategories().size());
        assertEquals(expectedOrdersSize, category.getOrders().size());
        Mockito.verify(orderDao, Mockito.times(1)).save(order);
        Mockito.verify(categoryDao, Mockito.times(1)).save(category);
    }

    @Test
    @DisplayName("checks if all orders will be returned")
    void testFindAllOrders() {
        //Act
        orderService.findAllOrders();

        //Assert
        Mockito.verify(orderDao, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("checks if the order will be found by the id")
    void testFindById() {
        //Arrange
        int id = 1;

        //Act
        orderService.findById(id);

        //Assert
        Mockito.verify(orderDao, Mockito.times(1)).findById(id);
    }

    @Test
    @DisplayName("checks if the order will be found by the category")
    void testFindOrdersByCategory() {
        //Arrange
        List<Order> expectedOrderList = category.getOrders();

        //Act
        List<Order> actualOrderList = orderService.findOrdersByCategory(category);

        //Assert
        assertEquals(expectedOrderList, actualOrderList);
    }
}