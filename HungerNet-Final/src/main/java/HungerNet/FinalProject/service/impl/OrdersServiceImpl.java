package HungerNet.FinalProject.service.impl;

import HungerNet.FinalProject.model.entity.Orders;
import HungerNet.FinalProject.model.entity.Restaurant;
import HungerNet.FinalProject.model.entity.User;
import HungerNet.FinalProject.model.entity.enums.OrderStatusEnum;
import HungerNet.FinalProject.repository.OrdersRepository;
import HungerNet.FinalProject.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public Optional<Orders> findById(Integer id) {
        return ordersRepository.findById(id);
    }

    @Override
    public List<Orders> findAllOrders() {
        return ordersRepository.findAll();
    }

    @Override
    public List<Orders> findAllOrdersByRestaurant(Integer restaurant) {
        return ordersRepository.findOrdersByRestaurant(restaurant);

    }

    @Override
    public void saveOrder(Orders order) {
        ordersRepository.save(order);
    }

    @Override
    public void deleteOrder(Orders order) {

    }
    @Override
    public String getStatusByOrderID(Integer id) {
        return ordersRepository.getStatusByOrderID(id);
    }

    @Override
    public List<Orders> findAllOrdersByUser(User user) {
        return ordersRepository.findOrdersByUserOrderedByDate(user,Sort.by("orderStartDate").descending());
    }

    @Override
    public List<Orders> findAllOrdersByStatus(String status) {
        return ordersRepository.findOrdersByStatus(OrderStatusEnum.valueOf(status));
    }

    @Override
    public List<Orders> findOrdersByUserOrderedByStatus(Integer user, String status) {
        return ordersRepository.findOrdersByUserOrderedByStatus(user,status);
    }

    @Override
    public List<Orders> findOrdersByRestaurantFilterByStatus(Integer user, String status) {
        return ordersRepository.findOrdersByRestaurantFilterByStatus(user,status);
    }
}
