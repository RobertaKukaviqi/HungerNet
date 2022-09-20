package HungerNet.FinalProject.repository;

import HungerNet.FinalProject.model.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Transactional
    @Query("Select user.restaurants from User user where user.username=?1")
    List<Restaurant> findAllRestaurantsByUserUsername(String username);

}
