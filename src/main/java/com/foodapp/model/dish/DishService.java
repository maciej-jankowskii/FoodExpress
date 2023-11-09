package com.foodapp.model.dish;

import com.foodapp.model.restaurant.Restaurant;
import com.foodapp.model.restaurant.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final DishMapper dishMapper;
    private final RestaurantRepository restaurantRepository;

    public DishService(DishRepository dishRepository, DishMapper dishMapper, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.dishMapper = dishMapper;
        this.restaurantRepository = restaurantRepository;
    }

    public Dish findDishById(Long id){
        return dishRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Dish not found"));
    }

    public DishDTO editDishById(DishDTO dto){
        Dish dish = dishRepository.findById(dto.getId()).orElseThrow(() -> new NoSuchElementException("Dish not found"));
        Dish saved = editDish(dto, dish);
        return dishMapper.map(saved);
    }

    public void deleteDishById(Long dishId){
        dishRepository.deleteById(dishId);
    }
    @Transactional
    public DishDTO addNewDish(Long restaurantId, DishDTO dto){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NoSuchElementException("Restaurant not found"));
        Dish dish = createNewDish(dto, restaurant);
        Dish saved = dishRepository.save(dish);
        return dishMapper.map(saved);
    }

    private Dish createNewDish(DishDTO dto, Restaurant restaurant) {
        Dish dish = dishMapper.map(dto);
        dish.setId(dto.getId());
        dish.setRestaurant(restaurant);
        dish.setName(dto.getName());
        dish.setPrice(dto.getPrice());
        dish.setDescription(dto.getDescription());
        restaurant.getDishes().add(dish);
        restaurantRepository.save(restaurant);
        return dish;
    }

    private Dish editDish(DishDTO dto, Dish dish) {
        dish.setId(dish.getId());
        dish.setPrice(dto.getPrice());
        dish.setName(dto.getName());
        dish.setDescription(dto.getDescription());
        Dish saved = dishRepository.save(dish);
        return saved;
    }
}
