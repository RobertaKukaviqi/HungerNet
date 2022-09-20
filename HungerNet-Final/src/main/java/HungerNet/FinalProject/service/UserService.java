package HungerNet.FinalProject.service;

import HungerNet.FinalProject.model.dto.UserDTO;
import HungerNet.FinalProject.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService extends UserDetailsService {
    Optional<User> findById(Integer id);

    Optional<User> findByUsername(String username);

    List<User> findAllRestaurantManagers();

    List<User> findAllUsers();

    List<User> findAllUsersByRole(String role);

    User getLoggedInUsser();

    void saveUser(UserDTO user) throws MessagingException, UnsupportedEncodingException;

    void deleteUser(Integer id);

    void enableUser(Integer id);
}
