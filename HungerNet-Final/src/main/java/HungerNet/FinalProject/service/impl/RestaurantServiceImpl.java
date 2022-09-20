package HungerNet.FinalProject.service.impl;


import HungerNet.FinalProject.model.entity.Restaurant;

import HungerNet.FinalProject.repository.RestaurantRepository;

import HungerNet.FinalProject.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;


    @Override
    public Optional<Restaurant> findById(Integer id) {
        return restaurantRepository.findById(id);
    }

    @Override
    public List<Restaurant> findAllRestaurants() {
        return restaurantRepository.findAll();
    }


    @Override
    public List<Restaurant> findAllManagerRestaurants(String username) {
        return restaurantRepository.findAllRestaurantsByUserUsername(username);
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Restaurant restaurant) {
        restaurantRepository.delete(restaurant);
    }
}
