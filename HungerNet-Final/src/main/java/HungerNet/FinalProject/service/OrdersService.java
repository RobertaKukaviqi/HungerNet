package HungerNet.FinalProject.service;

import HungerNet.FinalProject.model.entity.Menu;
import HungerNet.FinalProject.model.entity.Orders;
import HungerNet.FinalProject.model.entity.Restaurant;
import HungerNet.FinalProject.model.entity.User;
import HungerNet.FinalProject.model.entity.enums.OrderStatusEnum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrdersService {
    Optional<Orders> findById(Integer id);

    List<Orders> findAllOrders();

    void saveOrder(Orders order);

    List<Orders> findAllOrdersByRestaurant(Integer restaurant);

    void deleteOrder(Orders order);

    List<Orders> findAllOrdersByUser(User user);

    String getStatusByOrderID(Integer id);

    List<Orders> findAllOrdersByStatus(String status);

    List<Orders> findOrdersByUserOrderedByStatus(Integer user, String status);

    List<Orders> findOrdersByRestaurantFilterByStatus(Integer user, String status);
}
