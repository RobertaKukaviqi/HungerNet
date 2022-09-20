package HungerNet.FinalProject.controllers;

import HungerNet.FinalProject.model.dto.UserDTO;
import HungerNet.FinalProject.model.dto.UserDtoResponse;
import HungerNet.FinalProject.model.entity.User;
import HungerNet.FinalProject.configurations.jwtconfig.payload.MessageResponse;
import HungerNet.FinalProject.service.RestaurantService;
import HungerNet.FinalProject.service.UserDetailsService;
import HungerNet.FinalProject.service.UserService;
import org.apache.catalina.mapper.Mapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
*
* @project HungerNet
* @author  Roberta on 09/2022
*/
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private RestaurantService restaurantService;
    private static final Logger logger = LogManager.getLogger(UserController.class);
    private final UserDetailsService userDetailsService;

    private Mapper mapper;
    UserDtoResponse userDtoResponse = new UserDtoResponse();
    UserDTO userDTO = new UserDTO();

    public UserController(UserService userService, UserDetailsService userDetailsService) {
        super();
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    //get all users filter by role
    @GetMapping("/all")
    public List<UserDtoResponse> getAllUsers(@RequestParam(required = false) String role) {


        User loggedInUser = userService.getLoggedInUsser();

        logger.info(loggedInUser.getUsername() + " is running getAllUsersByRole() method");


        try {
            if (role != null) {
                return userService.findAllUsersByRole(role)
                        .stream()
                        .map(user -> userDtoResponse.convertUserToDto(user))
                        .collect(Collectors.toList());
            } else {
                return userService.findAllUsers()
                        .stream()
                        .map(user -> userDtoResponse.convertUserToDto(user))
                        .collect(Collectors.toList());
            }

        } catch (Exception exception) {
            logger.info(exception.getMessage());
            return null;
        }

    }

    //register a new user
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userRequest) throws Exception {

        Date today = new Date();

        Optional<User> userToCreate = userService.findByUsername(userRequest.getUsername());

        if(userService.getLoggedInUsser() != null) {
            User loggedInUser = userService.getLoggedInUsser();
            logger.info(loggedInUser.getUsername() + " is running register() method");
            userRequest.setCreatedBy(loggedInUser.getUsername());
            userRequest.setModifiedBy(loggedInUser.getUsername());
        }

        if (userToCreate.isPresent()) {
            return ResponseEntity.ok(new MessageResponse(userRequest.getUsername() + " already exist"));
        }
        userRequest.setNonLocked(true);
        userRequest.setEnabled(false);

        if (userRequest.getRoles().contains(2)) {
            if (userRequest.getRestaurants().isEmpty()) {
                return ResponseEntity.ok(new MessageResponse("Restaurant manager should be assigned to at least 1 existing restaurant"));
            }
        }


        try {

            userService.saveUser(userRequest);

            Optional<User> userCreated = userService.findByUsername(userRequest.getUsername());

            HungerNet.FinalProject.model.entity.UserDetails userDetailsCreated = userDTO.convertDtoToUserDetails(userRequest);
            userCreated.ifPresent(userDetailsCreated::setUser);
            userDetailsCreated.setCreatedDate(today);
            userDetailsService.saveUser(userDetailsCreated);

        } catch (Exception exception) {
            logger.error(exception.getMessage());
            return ResponseEntity.ok(new MessageResponse(exception.getMessage()));
        }
        logger.info(userRequest.getUsername() + " is saved successfully");
        return ResponseEntity.ok(new MessageResponse(userRequest.getUsername() + " is saved successfully"));


    }


    @PostMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody UserDTO userRequest) {

        Date today = new Date();

        Optional<User> userToCreate = userService.findByUsername(userRequest.getUsername());

        User loggedInUser = userService.getLoggedInUsser();

        logger.info(loggedInUser.getUsername() + " is running update() method");

        if (userToCreate.isPresent()) {
            return ResponseEntity.ok(new MessageResponse(userRequest.getUsername() + " already exist"));
        }
        userRequest.setCreatedBy(loggedInUser.getUsername());
        userRequest.setModifiedBy(loggedInUser.getUsername());
        userRequest.setNonLocked(true);
        userRequest.setEnabled(false);

        if (userRequest.getRoles().contains(2)) {
            if (userRequest.getRestaurants().isEmpty()) {
                return ResponseEntity.ok(new MessageResponse("Restaurant manager should be assigned to at least 1 existing restaurant"));
            }
        }


        try {

            userService.saveUser(userRequest);

            Optional<User> userCreated = userService.findByUsername(userRequest.getUsername());

            HungerNet.FinalProject.model.entity.UserDetails userDetailsCreated = userDTO.convertDtoToUserDetails(userRequest);
            userCreated.ifPresent(userDetailsCreated::setUser);
            userDetailsCreated.setCreatedDate(today);
            userDetailsService.saveUser(userDetailsCreated);

        } catch (Exception exception) {
            logger.error(exception.getMessage());
            return ResponseEntity.ok(new MessageResponse(exception.getMessage()));
        }
        logger.info(userRequest.getUsername() + " is updated successfully");
        return ResponseEntity.ok(new MessageResponse(userRequest.getUsername() + " is updated successfully"));


    }

    //get user by id
    @GetMapping("/{id}")
    @ResponseBody
    public List<UserDtoResponse> getUser(@PathVariable Integer id) {
        if(userService.getLoggedInUsser() != null) {
            User loggedInUser = userService.getLoggedInUsser();
            logger.info(loggedInUser.getUsername() + " is running getAllRestaurantManager() method");
        }
        return userService.findById(id)
                .stream()
                .map(user -> userDtoResponse.convertUserToDto(user))
                .collect(Collectors.toList());

    }

    //delete user
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteUser(@PathVariable String id) throws Exception {

        User loggedInUser = userService.getLoggedInUsser();

        logger.info(loggedInUser.getUsername() + " is running deleteUser() method");

        if (id.matches("\\d+")) {

            Optional<User> user = userService.findById(Integer.parseInt(id));
            if (user.isPresent()) {
                try {
                    userService.deleteUser(Integer.parseInt(id));
                } catch (Exception exception) {

                    return ResponseEntity.ok(new MessageResponse(exception.getMessage()));
                }
            } else {
                logger.info("User with id " + id + " does not exist");
                return ResponseEntity.ok(new MessageResponse("User with id " + id + " does not exist"));
            }
            logger.info(loggedInUser.getUsername() + " deleted user with id" + id);
            return ResponseEntity.ok(new MessageResponse("User is deleted successfully"));
        } else
        {
            logger.info("Id is not integer");
            return ResponseEntity.ok(new MessageResponse("The id should be integer only!"));
        }

    }

    //enable user
    @GetMapping("/enable/{id}")
    @ResponseBody
    public ResponseEntity<?> enableUser(@PathVariable Integer id) {
        User loggedInUser = userService.getLoggedInUsser();
        logger.info(loggedInUser.getUsername() + " is running enableUser() method");
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            userService.enableUser(id);
            logger.info(loggedInUser.getUsername() + " enabled user with id " + id);
            return ResponseEntity.ok(new MessageResponse("user with id " + id + " is enabled successfully"));
        }
        else {
            logger.info("User with id " + id + " does not exist");
            return ResponseEntity.ok(new MessageResponse("User with id " + id + " does not exist"));
        }
    }

}
