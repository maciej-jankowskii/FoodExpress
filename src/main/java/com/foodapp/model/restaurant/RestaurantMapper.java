package com.foodapp.model.restaurant;

import com.foodapp.model.address.AddressRepository;
import com.foodapp.model.dish.DishRepository;
import com.foodapp.model.enums.Cuisine;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RestaurantMapper {

    private final AddressRepository addressRepository;
    private final DishRepository dishRepository;

    public RestaurantMapper(AddressRepository addressRepository, DishRepository dishRepository) {
        this.addressRepository = addressRepository;
        this.dishRepository = dishRepository;
    }

    public Restaurant map(RestaurantDTO dto){
        Restaurant restaurant = new Restaurant();
        restaurant.setId(dto.getId());
        restaurant.setName(dto.getName());
        restaurant.setTypeOfCuisine(Cuisine.valueOf(dto.getTypeOfCuisine()));
//        Address address = addressRepository.findById(dto.getAddressId()).orElse(null);
//        restaurant.setAddress(address);
//        List<Dish> dishes = dishRepository.findAllByRestaurantId(dto.getId());
//        restaurant.setDishes(dishes);
        restaurant.setAverageRating(dto.getAverageRating());
        return restaurant;
    }

    public RestaurantDTO map(Restaurant restaurant){
        RestaurantDTO dto = new RestaurantDTO();
        dto.setId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setAddressId(restaurant.getAddress().getId());
        dto.setTypeOfCuisine(String.valueOf(restaurant.getTypeOfCuisine()));
        dto.setAverageRating(restaurant.getAverageRating());
        return dto;
    }

}
