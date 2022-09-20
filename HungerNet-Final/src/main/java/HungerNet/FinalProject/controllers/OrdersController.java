package HungerNet.FinalProject.controllers;

import HungerNet.FinalProject.model.dto.MenuDto;
import HungerNet.FinalProject.model.dto.OrdersDto;
import HungerNet.FinalProject.model.entity.Menu;
import HungerNet.FinalProject.model.entity.Orders;
import HungerNet.FinalProject.model.entity.Restaurant;
import HungerNet.FinalProject.model.entity.User;
import HungerNet.FinalProject.model.entity.enums.OrderStatusEnum;
import HungerNet.FinalProject.configurations.jwtconfig.payload.MessageResponse;
import HungerNet.FinalProject.service.MenuService;
import HungerNet.FinalProject.service.OrdersService;
import HungerNet.FinalProject.service.RestaurantService;
import HungerNet.FinalProject.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
public class OrdersController {
    private static final Logger logger = LogManager.getLogger(OrdersController.class);
    @Autowired
    private MenuService menuService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrdersService ordersService;
    Date today = new Date();

    //make a new order
    @PostMapping("/order/save")
    public ResponseEntity<?> makeOrder(@RequestParam Integer menuId) throws Exception {

        MenuDto menuDto = new MenuDto();
        Orders order = new Orders();
        User loggedInUser = userService.getLoggedInUsser();

        Optional<Menu> menu = menuService.findById(menuId);


        if (menu.isEmpty()) {
            logger.info("Menu  " + menuId + " does not exists");
            return ResponseEntity.ok(new MessageResponse("Menu " + menuId + " does not exists"));
        }
        logger.info(loggedInUser.getUsername() + " is making an order");

        order.setOrderedBy(loggedInUser);
        order.setOrderStartDate(today);
        order.setStatus(OrderStatusEnum.CREATED);
        order.setStastusUpdatedAt(today);
        if (menuService.findById(menuId).isPresent()) {
            order.setMenu(menuService.findById(menuId).get());
        }
        boolean isClient = loggedInUser.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("CLIENT"));
        logger.info(loggedInUser.getUsername() + " is running getAllOrders() method");
        if (isClient) {


            try {

                ordersService.saveOrder(order);

            } catch (Exception exception) {
                logger.error(exception.getMessage());
                return ResponseEntity.ok(new MessageResponse(exception.getMessage()));
            }
            logger.info("Order number " + order.getOrderNumber() + " is saved successfully by " + loggedInUser.getUsername());
            return ResponseEntity.ok(new MessageResponse("Order number " + order.getOrderNumber() + " is saved successfully by " + loggedInUser.getUsername()));
        } else {
            return ResponseEntity.ok(new MessageResponse(loggedInUser.getUsername() + " is not a client"));
        }


    }

    //List all Client orders
    @PreAuthorize("hasAnyAuthority('CLIENT')")
    @GetMapping("order/client/all")
    public List<OrdersDto> getAllOrders(@RequestParam(required = false) String status) {
        OrdersDto ordersDto = new OrdersDto();
        User loggedInUser = userService.getLoggedInUsser();

        logger.info(loggedInUser.getUsername() + " is running getAllOrders() method");
        try {
            //if status is given filter by status
            if (status == null) {
                return ordersService.findAllOrdersByUser(loggedInUser).stream()
                        .map(ordersDto::convertItemToDto).collect(Collectors.toList());

            } else {
                status = status.toUpperCase();
                return ordersService.findOrdersByUserOrderedByStatus(loggedInUser.getId(), status).stream()
                        .map(ordersDto::convertItemToDto).collect(Collectors.toList());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }

    }

    //List all orders by Restaurant
    @PreAuthorize("hasAnyAuthority('RESTAURANT_MANAGER')")
    @GetMapping("order/all")
    public List<OrdersDto> getAllOrdersByRestaurant(@RequestParam(required = false) Integer restaurantId,String status) {
        OrdersDto ordersDto = new OrdersDto();
        User loggedInUser = userService.getLoggedInUsser();
        logger.info(loggedInUser.getUsername() + " is running getAllOrders() method");
        Optional<Restaurant> restaurant = restaurantService.findById(restaurantId);
        if (restaurant.isEmpty()) {
            logger.info("Required restaurant does not exists!");
            return null;
        }
        boolean isMyRestaurant = loggedInUser.getRestaurants().stream().anyMatch(r -> r.getId().equals(restaurantId));

        if (!isMyRestaurant) {
            logger.info("This is not " + loggedInUser.getUsername() + " restaurant!");
            return null;
        }

        try {
            if (status != null) {
                return ordersService.findOrdersByRestaurantFilterByStatus(restaurantId,status).stream()
                        .map(ordersDto::convertItemToDto).collect(Collectors.toList());

            }else {
                return ordersService.findAllOrdersByRestaurant(restaurantId).stream()
                        .map(ordersDto::convertItemToDto).collect(Collectors.toList());
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }


    }


    //Change order status
    @PreAuthorize("hasAnyAuthority('RESTAURANT_MANAGER')")
    @PutMapping("order")
    public ResponseEntity<?> updateStatus(@RequestParam(required = false) String orderNumber, String status) {
        String nextStatus="";
        User loggedInUser = userService.getLoggedInUsser();
        String currentStatus=ordersService.getStatusByOrderID(Integer.parseInt(orderNumber));
        if(currentStatus.equals("DELIVERED")){
            return ResponseEntity.ok(new MessageResponse("This order has been delivered"));
        }

        List<OrderStatusEnum> orderStatus=Arrays.asList(OrderStatusEnum.values());
        int currentIndex= orderStatus.indexOf(OrderStatusEnum.valueOf(currentStatus));
        if(currentIndex==0){
            if(status==null){
                return ResponseEntity.ok(new MessageResponse("This order has needs to be approved or rejected first!"));
            }
            else {
                nextStatus = status;
            }
        }else if(currentStatus.equals("REJECTED")){
            return ResponseEntity.ok(new MessageResponse("This order has already been rejected!"));

        }
        else{
            nextStatus = orderStatus.get(currentIndex + 1).name();
        }

        try {
            Orders order = ordersService.findById(Integer.parseInt(orderNumber)).get();

            boolean isMyRestaurant = loggedInUser.getRestaurants().stream().anyMatch(r -> r.getId().equals(order.getMenu().getRestaurant().getId()));
            if (isMyRestaurant) {
                order.setStatus(OrderStatusEnum.valueOf(nextStatus));
                order.setStastusUpdatedAt(today);
                ordersService.saveOrder(order);
            } else {
                logger.info(loggedInUser.getUsername() + " is trying to update order of " + order.getMenu().getRestaurant().getName() + " that is not one of the restaurants he/she is managing!");
                return ResponseEntity.ok(loggedInUser.getUsername() + " is trying to update order of " + order.getMenu().getRestaurant().getName() + " that is not one of the restaurants he/she is managing!");
            }
        } catch (Exception exception) {
            logger.info(exception.getMessage());
            return ResponseEntity.ok(new MessageResponse(exception.getMessage()));
        }

        return ResponseEntity.ok(new MessageResponse(loggedInUser.getUsername() + " changed status to " + nextStatus));
    }

}



