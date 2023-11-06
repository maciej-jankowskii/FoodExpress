package com.foodapp.model.dish;

import com.foodapp.model.restaurant.Restaurant;
import com.foodapp.model.restaurant.RestaurantRepository;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
public class DishMapper {

    private final RestaurantRepository restaurantRepository;

    public DishMapper(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public DishDTO map(Dish dish){
        DishDTO dto = new DishDTO();
        dto.setName(dish.getName());
        dto.setDescription(dish.getDescription());
        dto.setPrice(dish.getPrice());
        dto.setRestaurantId(dish.getRestaurant().getId());
        return dto;
    }
    public Dish map(DishDTO dishDTO){
        Dish dish = new Dish();
        dish.setName(dishDTO.getName());
        dish.setDescription(dishDTO.getDescription());
        dish.setPrice(dishDTO.getPrice());
        Restaurant restaurant = restaurantRepository.findRestaurantByDishId(dishDTO.getRestaurantId()).orElse(null);
        dish.setRestaurant(restaurant);
        return dish;
    }
}
