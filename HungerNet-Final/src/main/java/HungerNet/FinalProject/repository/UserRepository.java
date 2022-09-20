package HungerNet.FinalProject.repository;

import HungerNet.FinalProject.model.entity.User;
import HungerNet.FinalProject.model.entity.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    List<User> findUserByRoles_Role(RoleEnum role);

    List<User> findUserByRoles_RoleNot(RoleEnum role);


    @Transactional
    @Query("Select user from User user where user.enabled = 1")
    List<User> findAllEnabledUsers();

    @Transactional
    @Modifying
    @Query("Update User user set user.enabled = 0 where user.id = ?1")
    void disableUser(Integer id);

    @Transactional
    @Modifying
    @Query("Update User user set user.enabled = 1 where user.id = ?1")
    void enableUser(Integer id);
}
