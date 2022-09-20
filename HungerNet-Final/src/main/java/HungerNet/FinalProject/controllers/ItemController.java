package HungerNet.FinalProject.controllers;

import HungerNet.FinalProject.model.dto.ItemDto;
import HungerNet.FinalProject.model.dto.MenuDto;
import HungerNet.FinalProject.model.entity.Item;
import HungerNet.FinalProject.model.entity.Menu;
import HungerNet.FinalProject.model.entity.User;
import HungerNet.FinalProject.configurations.jwtconfig.payload.MessageResponse;
import HungerNet.FinalProject.service.ItemService;
import HungerNet.FinalProject.service.MenuService;
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
public class ItemController {

    private static final Logger logger = LogManager.getLogger(ItemController.class);
    @Autowired
    private MenuService menuService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;

    Date today = new Date();
    MenuDto menuDto = new MenuDto();
    ItemDto itemDto = new ItemDto();


    //get all items
    @GetMapping("restaurant/menu/item/all")
    public List<ItemDto> getAllItems() {
        User loggedInUser = userService.getLoggedInUsser();
        logger.info(loggedInUser.getUsername() + " is running getAllItems() method");
        return itemService.findAllItems()
                .stream()
                .map(item -> itemDto.convertItemToDto(item))
                .collect(Collectors.toList());
    }

    //get specific item
    @GetMapping("restaurant/menu/item/{id}")
    @ResponseBody
    public ItemDto getItem(@PathVariable Integer id) {
        User loggedInUser = userService.getLoggedInUsser();
        logger.info(loggedInUser.getUsername() + " is running getItem() method");
        Optional<Item> item = itemService.findById(id);
        try {
            return itemDto.convertItemToDto(item.get());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    //get items from specific menu
    @GetMapping("restaurant/menu/{id}/item/all")
    @ResponseBody
    public List<ItemDto> getAllMenuItems(@PathVariable Integer id) {
        User loggedInUser = userService.getLoggedInUsser();
        logger.info(loggedInUser.getUsername() + " is running getAllMenuItems() method");
        if (menuService.findById(id).isPresent()) {
            menuService.findById(id).get();
        } else {
            return null;
        }


        Menu menu = menuService.findById(id).get();
        return menu.getItems()
                .stream()
                .map(item -> itemDto.convertItemToDto(item))
                .collect(Collectors.toList());

    }

    //register a new item
    @PreAuthorize("hasAnyAuthority('RESTAURANT_MANAGER')")
    @PostMapping("/item/register")
    public ResponseEntity<?> saveItem(@RequestBody ItemDto itemDto) {
        User loggedInUser = userService.getLoggedInUsser();

        Item item;

        logger.info(loggedInUser.getUsername() + " is running saveItem() method");

        Optional<Menu> menu = menuService.findById(itemDto.getMenu());

        //check if menu exists
        if (menu.isEmpty()) {
            logger.info("Menu with id " + itemDto.getMenu() + " does not exists");
            return ResponseEntity.ok(new MessageResponse("Menu with id " + itemDto.getMenu() + " does not exists"));
        }
        boolean isMyRestaurantMenu = loggedInUser.getRestaurants().stream().anyMatch(r -> r.getId().equals(menu.get().getRestaurant().getId()));

        //check if this menu belongs to any of my restaurants
        if (isMyRestaurantMenu) {
            item = itemDto.convertDtoToItem(itemDto);
            item.setDateCreated(today);
            item.setDateModified(today);
            item.setMenu(menu.get());

            try {
                itemService.saveItem(item);
            } catch (Exception exception) {
                logger.error(exception.getMessage());
                return ResponseEntity.ok(new MessageResponse(exception.getMessage()));
            }

            logger.info(loggedInUser.getUsername() + " registered " + item.getName());

            return ResponseEntity.ok(new MessageResponse(item.getName() + " is saved successfully to " + menu.get().getName()));

        } else {
            logger.info(loggedInUser.getUsername() + " is trying to register items to a menu from another restaurant!");
            return null;
        }
    }

    //update item
    @PreAuthorize("hasAnyAuthority('RESTAURANT_MANAGER')")
    @PostMapping("/item/update")
    public ResponseEntity<?> updateItem(@RequestBody ItemDto itemDto) {
        User loggedInUser = userService.getLoggedInUsser();

        Item item;

        logger.info(loggedInUser.getUsername() + " is running saveItem() method");

        Optional<Menu> menu = menuService.findById(itemDto.getMenu());

        //check if menu exists
        if (menu.isEmpty()) {
            logger.info("Item with id " + itemDto.getId() + " does not exist");
            return ResponseEntity.ok(new MessageResponse("Item with id " + itemDto.getId() + " does not exist"));
        }
        //check if this menu belongs to any of my restaurants
        boolean isMyRestaurantMenu = loggedInUser.getRestaurants().stream().anyMatch(r -> r.getId().equals(menu.get().getRestaurant().getId()));
        if (isMyRestaurantMenu) {
            item = itemDto.convertDtoToItem(itemDto);
            item.setDateCreated(today);
            item.setDateModified(today);
            item.setId(itemDto.getId());
            item.setMenu(menu.get());
            try {
                itemService.saveItem(item);
            } catch (Exception exception) {
                logger.error(exception.getMessage());
                return ResponseEntity.ok(new MessageResponse(exception.getMessage()));
            }
            logger.info(loggedInUser.getUsername() + " registered " + item.getName());

            return ResponseEntity.ok(new MessageResponse(item.getName() + " is saved successfully"));
        } else {
            logger.info(loggedInUser.getUsername() + " is trying to update items to a menu from another restaurant!");
            return null;
        }
    }

    //delete specific item
    @PreAuthorize("hasAnyAuthority('RESTAURANT_MANAGER')")
    @DeleteMapping("item/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteRestaurant(@PathVariable String id) {

        User loggedInUser = userService.getLoggedInUsser();
        logger.info(loggedInUser.getUsername() + " is running deleteRestaurant() method");
        //check if the variable value is integer
        if (id.matches("\\d+")) {
            Item item;
            try {
                item = itemService.findById(Integer.parseInt(id)).get();
                itemService.deleteItem(item);
            } catch (Exception exception) {
                //check if item is present
                if (exception.getMessage() == "No value present") {
                    logger.info("Item with id " + id + " does not exist");
                    return ResponseEntity.ok(new MessageResponse("Item with id " + id + " does not exist"));
                } else {
                    return ResponseEntity.ok(new MessageResponse(exception.getMessage()));
                }
            }
            //check if this item belongs to my restaurant
            boolean isMyRestaurantMenu = loggedInUser.getRestaurants().stream().anyMatch(r -> r.getId().equals(item.getMenu().getRestaurant().getId()));
            if (isMyRestaurantMenu) {
                logger.info(loggedInUser.getUsername() + " deleted " + item.getName());
                return ResponseEntity.ok(new MessageResponse(item.getName() + " is deleted successfully"));
            } else {
                logger.info(loggedInUser.getUsername() + " is trying to delete items from a menu from another restaurant!");
                return null;
            }
        } else {
            return ResponseEntity.ok(new MessageResponse("The id should be integer only!"));
        }

    }

}
