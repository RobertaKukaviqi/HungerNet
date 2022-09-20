package HungerNet.FinalProject.controllers;

import HungerNet.FinalProject.model.dto.MenuDto;
import HungerNet.FinalProject.model.dto.RestaurantDto;
import HungerNet.FinalProject.model.entity.Menu;
import HungerNet.FinalProject.model.entity.Restaurant;
import HungerNet.FinalProject.model.entity.User;
import HungerNet.FinalProject.configurations.jwtconfig.payload.MessageResponse;
import HungerNet.FinalProject.service.MenuService;
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
public class MenuController {

    private static final Logger logger = LogManager.getLogger(MenuController.class);
    @Autowired
    private MenuService menuService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;

    Date today = new Date();
    MenuDto menuDto = new MenuDto();
    RestaurantDto restaurantDto = new RestaurantDto();

    //get all menus from my restaurants
    @GetMapping("restaurant/menu/all")
    public List<MenuDto> getAllMenus() {
        User loggedInUser = userService.getLoggedInUsser();
        logger.info(loggedInUser.getUsername() + " is running getAllMenus() method");
        List<MenuDto> menus = new ArrayList<>();
        List<RestaurantDto> restaurants;
        restaurants = restaurantService.findAllRestaurants().stream()
                .map(restaurant -> restaurantDto.convertRestaurantToDto(restaurant))
                .collect(Collectors.toList());
        ;
        restaurants.forEach(restaurant -> menus.addAll(restaurant.getMenus()));
        return menus;
    }

    //get all menus
    @GetMapping("menu/all")
    public List<MenuDto> getMenus() {
        User loggedInUser = userService.getLoggedInUsser();
        logger.info(loggedInUser.getUsername() + " is running getMenus() method");
        List<MenuDto> menus = new ArrayList<>();
        menus = menuService.findAllMenus().stream()
                .map(menu -> menuDto.convertMenuToDto(menu))
                .collect(Collectors.toList());
        ;
        return menus;
    }

    //get all menus from specific restaurant
    @GetMapping("restaurant/{id}/menu/all")
    @ResponseBody
    public List<MenuDto> getRestaurant(@PathVariable Integer id) {
        if(userService != null) {
            User loggedInUser = userService.getLoggedInUsser();
            logger.info(loggedInUser.getUsername() + " is running getMenu() method");
        }
        return menuService.findMenuByRestaurantId(id)
                .stream()
                .map(menu -> menuDto.convertMenuToDto(menu))
                .collect(Collectors.toList());
    }

    //add a new menu
    @PreAuthorize("hasAnyAuthority('RESTAURANT_MANAGER')")
    @PostMapping("/menu/register")
    public ResponseEntity<?> saveMenu(@RequestBody MenuDto menuDto) {
        User loggedInUser = new User();
        if(userService != null)
        loggedInUser = userService.getLoggedInUsser();

        Menu menu = new Menu();
        Optional<Restaurant> restaurant;
        logger.info(loggedInUser.getUsername() + " is running saveMenu() method");
        try {

            restaurant = restaurantService.findById(menuDto.getRestaurant());


        } catch (Exception exception) {
            logger.error(exception.getMessage());
            return ResponseEntity.ok(new MessageResponse(exception.getMessage()));
        }
        //check if restaurant exists
        if (restaurant.isEmpty()) {
            logger.info("Restaurant with id " + menuDto.getRestaurant() + " does not exists");
            return ResponseEntity.ok(new MessageResponse("Restaurant with id " + menuDto.getRestaurant() + " does not exists"));
        }
        //check if is my restaurant
        boolean isMyRestaurant = loggedInUser.getRestaurants().stream().anyMatch(r->r.getId().equals(restaurant.get().getId()));
        if(isMyRestaurant){
        menu = menuDto.convertDtoToMenu(menuDto);
        menu.setDateCreated(today);
        menu.setDateModified(today);
        menu.setRestaurant(restaurant.get());
        try {
            menuService.saveMenu(menu);
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            return ResponseEntity.ok(new MessageResponse(exception.getMessage()));
        }
        logger.info(loggedInUser.getUsername() + " registered " + menu.getName());

        return ResponseEntity.ok(new MessageResponse(menu.getName() + " is saved successfully"));}
        else {
            logger.info(loggedInUser.getUsername() + " is trying to register menu to " + restaurant.get().getName()+" that is not one of the restaurants he/she is managing!");
            return ResponseEntity.ok(loggedInUser.getUsername() + " is trying to register menu to " + restaurant.get().getName()+" that is not one of the restaurants he/she is managing!");
        }
    }

    @PreAuthorize("hasAnyAuthority('RESTAURANT_MANAGER')")
    @PostMapping("/menu/update")
    public ResponseEntity<?> updateMenu(@RequestBody MenuDto menuDto) {
        User loggedInUser = userService.getLoggedInUsser();

        Menu menu = new Menu();

        logger.info(loggedInUser.getUsername() + " is running updateMenu() method");

        Optional<Restaurant> restaurant = restaurantService.findById(menuDto.getRestaurant());
        //check if restaurant exists
        if (restaurant.isEmpty()) {
            logger.info("Restaurant with id " + menuDto.getRestaurant() + " does not exists");
            return ResponseEntity.ok(new MessageResponse("Restaurant with id " + menuDto.getRestaurant() + " does not exists"));
        }
        //check if is my restaurant
        boolean isMyRestaurant = loggedInUser.getRestaurants().stream().anyMatch(r->r.getId().equals(restaurant.get().getId()));
        if(isMyRestaurant) {
            menu = menuDto.convertDtoToMenu(menuDto);
            menu.setDateModified(today);
            menu.setId(menuDto.getId());
            menu.setRestaurant(restaurant.get());
            try {
                menuService.saveMenu(menu);
            } catch (Exception exception) {
                logger.error(exception.getMessage());
                return ResponseEntity.ok(new MessageResponse(exception.getMessage()));
            }
            logger.info(loggedInUser.getUsername() + " registered " + menu.getName()+" to "+restaurant.get().getName());

            return ResponseEntity.ok(new MessageResponse(menu.getName() + " is saved successfully"));
        }else {
            logger.info(loggedInUser.getUsername() + " is trying to update menu of " + restaurant.get().getName()+" that is not one of the restaurants he/she is managing!");
            return null;
        }
    }
}
