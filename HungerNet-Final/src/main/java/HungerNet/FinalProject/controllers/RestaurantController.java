package HungerNet.FinalProject.controllers;

import HungerNet.FinalProject.model.dto.RestaurantDto;
import HungerNet.FinalProject.model.entity.Restaurant;
import HungerNet.FinalProject.model.entity.User;
import HungerNet.FinalProject.configurations.jwtconfig.payload.MessageResponse;
import HungerNet.FinalProject.service.RestaurantService;
import HungerNet.FinalProject.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {


    private static final Logger logger = LogManager.getLogger(RestaurantController.class);
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    private UserService userService;

    RestaurantDto restaurantDto = new RestaurantDto();

    Date today = new Date();

    @PreAuthorize("hasAnyAuthority('CLIENT')")
    @GetMapping("/all")
    public List<RestaurantDto> getAllRestaurants(HttpServletRequest request) {

        User loggedInUser = userService.getLoggedInUsser();
        logger.info(loggedInUser.getUsername() + " is running getAllRestaurants() method");
        return restaurantService.findAllRestaurants()
                .stream()
                .map(restaurant -> restaurantDto.convertRestaurantToDto(restaurant))
                .collect(Collectors.toList());

    }

    @GetMapping("/{id}")
    @ResponseBody
    public List<RestaurantDto> getRestaurant(@PathVariable Integer id) {
        User loggedInUser = userService.getLoggedInUsser();
        logger.info(loggedInUser.getUsername() + " is running getAllRestaurants() method");
        return restaurantService.findById(id)
                .stream()
                .map(restaurant -> restaurantDto.convertRestaurantToDto(restaurant))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<?> saveRestaurant(@RequestBody Restaurant restaurant) {
      try{
        User loggedInUser = userService.getLoggedInUsser();

        logger.info(loggedInUser.getUsername() + " is running saveRestaurant() method");

        restaurant.setDateCreated(today);

        restaurantService.saveRestaurant(restaurant);

        logger.info(loggedInUser.getUsername() + " registered " + restaurant.getName() + " restaurant");

        return ResponseEntity.ok(new MessageResponse(restaurant.getName() + " restaurant is saved successfully"));
    } catch (Exception exception) {
        logger.error(exception.getMessage());
        return ResponseEntity.ok(new MessageResponse(exception.getMessage()));
    }
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteRestaurant(@PathVariable String id) {

        User loggedInUser = userService.getLoggedInUsser();
        logger.info(loggedInUser.getUsername() + " is running deleteRestaurant() method");
        if (id.matches("\\d+")) {
            Restaurant restaurant = new Restaurant();
            try {
                restaurant = restaurantService.findById(Integer.parseInt(id)).get();
                restaurantService.deleteRestaurant(restaurant);
            } catch (Exception exception) {
                if (exception.getMessage() == "No value present") {
                    logger.info("Restaurant with id " + id + " does not exist");
                    return ResponseEntity.ok(new MessageResponse("Restaurant with id " + id + " does not exist"));
                } else {
                    return ResponseEntity.ok(new MessageResponse(exception.getMessage()));
                }
            }
            logger.info(loggedInUser.getUsername() + " deleted " + restaurant.getName());
            return ResponseEntity.ok(new MessageResponse(restaurant.getName() + " is deleted successfully"));
        } else {
            return ResponseEntity.ok(new MessageResponse("The id should be integer only!"));
        }

    }
}
