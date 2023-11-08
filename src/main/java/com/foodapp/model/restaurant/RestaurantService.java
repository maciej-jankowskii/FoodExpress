package com.foodapp.model.restaurant;

import com.foodapp.model.rating.Rating;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Restaurant not found"));
    }

    public Restaurant findByDishId(Long dishId) {
        return restaurantRepository.findRestaurantByDishId(dishId).orElseThrow();
    }

    public List<Restaurant> findAllRestaurants() {
        return restaurantRepository.findAll();
    }
    public void deleteRestaurantById(Long id){
        restaurantRepository.deleteById(id);
    }
}
