package HungerNet.FinalProject.service.impl;

import HungerNet.FinalProject.model.dto.UserDTO;
import HungerNet.FinalProject.model.entity.Restaurant;
import HungerNet.FinalProject.model.entity.Role;
import HungerNet.FinalProject.model.entity.User;

import HungerNet.FinalProject.model.entity.enums.RoleEnum;
import HungerNet.FinalProject.model.impl.UserDetailsImpl;
import HungerNet.FinalProject.repository.UserRepository;
import HungerNet.FinalProject.service.RestaurantService;
import HungerNet.FinalProject.service.RoleService;
import HungerNet.FinalProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.*;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    private final RestaurantService restaurantService;
    private final RoleService roleService;

    public UserServiceImpl(RoleService roleService, RestaurantService restaurantService) {
        super();
        this.roleService = roleService;
        this.restaurantService = restaurantService;

    }

    @Autowired
    private PasswordEncoder encoder;


    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAllRestaurantManagers() {

        RoleEnum roleEnum = RoleEnum.RESTAURANT_MANAGER;

        return userRepository.findUserByRoles_Role(roleEnum);
    }

    @Override
    public List<User> findAllUsers() {
        RoleEnum roleEnum = RoleEnum.ADMIN;

        return userRepository.findUserByRoles_RoleNot(roleEnum);

    }

    @Override
    public List<User> findAllUsersByRole(String role) {

        return userRepository.findUserByRoles_Role(RoleEnum.valueOf(role));

    }

    @Override
    public User getLoggedInUsser() {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Optional<User> user = findByUsername(userDetails.getUsername());
        return user.orElse(null);
    }

    @Override
    public void saveUser(UserDTO userDTO) throws UnsupportedEncodingException, MessagingException {

        User loggedinUser = findByUsername(userDTO.getCreatedBy()).get();

        User user = new User();

        Set<Role> role = new HashSet<>();

        List<Integer> roleIds = userDTO.getRoles();

        roleIds.forEach(id -> role.add(roleService.findById(id).get()));

        List<Restaurant> restaurant = new ArrayList<>();

        List<Integer> restaurantIds = userDTO.getRestaurants();

        restaurantIds.forEach(id -> restaurant.add(restaurantService.findById(id).get()));

        user.getRoles().addAll(role);

        user.setEnabled(userDTO.getEnabled());

        user.getRestaurants().addAll(restaurant);

        user.setUsername(userDTO.getUsername());

        user.setPassword(encoder.encode(userDTO.getPassword()));

        user.setCreatedBy(loggedinUser);

        user.setModifiedBy(loggedinUser);

        user.setAccountNonLocked(userDTO.getNonLocked());

        userRepository.save(user);
        // sendVerificationEmail(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.disableUser(id);
    }

    @Override
    public void enableUser(Integer id) {
        userRepository.enableUser(id);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(user);
    }
}
