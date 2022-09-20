package HungerNet.FinalProject.service;

import HungerNet.FinalProject.model.dto.RestaurantDto;
import HungerNet.FinalProject.model.entity.Restaurant;
import HungerNet.FinalProject.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RestaurantService {
    Optional<Restaurant> findById(Integer id);

    List<Restaurant> findAllRestaurants();
    List<Restaurant> findAllManagerRestaurants(String username);
    void saveRestaurant(Restaurant restaurant);

    void deleteRestaurant(Restaurant restaurant);
}
