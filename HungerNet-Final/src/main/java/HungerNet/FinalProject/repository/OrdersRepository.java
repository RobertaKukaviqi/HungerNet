package HungerNet.FinalProject.repository;

import HungerNet.FinalProject.model.entity.Orders;
import HungerNet.FinalProject.model.entity.Restaurant;
import HungerNet.FinalProject.model.entity.User;
import HungerNet.FinalProject.model.entity.enums.OrderStatusEnum;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {


    @Transactional
    @Query("Select orders from Orders orders where orders.orderedBy=?1")
    List<Orders> findOrdersByUserOrderedByDate(User user, Sort sort);

    List<Orders> findOrdersByStatus(OrderStatusEnum status);

    @Transactional
    @Query(nativeQuery = true, value = "select * from orders inner join menu on orders.menu_id=menu.id WHERE menu.restaurant_id=?1")
    List<Orders> findOrdersByRestaurant(Integer restaurant);


    @Transactional
    @Query(nativeQuery = true, value = "select orders.status from orders where orders.id=?1")
    String getStatusByOrderID(Integer id);

    @Transactional
    @Query(nativeQuery = true, value = "select * from orders inner join menu on orders.menu_id=menu.id WHERE menu.restaurant_id=?1 AND orders.status=?2")
    List<Orders> findOrdersByRestaurantFilterByStatus(Integer restaurant, String status);

    @Transactional
    @Query(nativeQuery = true, value = "select * from orders WHERE orders.ordered_by=?1 AND orders.status=?2")
    List<Orders> findOrdersByUserOrderedByStatus(Integer user, String status);
}
