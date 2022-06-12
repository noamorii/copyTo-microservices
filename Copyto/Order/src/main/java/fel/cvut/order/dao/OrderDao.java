package fel.cvut.order.dao;

import fel.cvut.order.model.Category;
import fel.cvut.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDao extends JpaRepository<Order, Integer> {

    @Query(value = " SELECT o FROM Order o WHERE :category MEMBER OF o.categories")
    List<Order> findAllOrdersByCategory1(@Param("category") Category category);

    @Query(value = " SELECT o FROM Order o WHERE :category IN o.categories")
    List<Order> findAllOrdersByCategory2(@Param("category") Category category); //idk if it works

}
