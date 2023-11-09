package com.foodapp.model.dish;

import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final DishMapper dishMapper;

    public DishService(DishRepository dishRepository, DishMapper dishMapper) {
        this.dishRepository = dishRepository;
        this.dishMapper = dishMapper;
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

    private Dish editDish(DishDTO dto, Dish dish) {
        dish.setId(dish.getId());
        dish.setPrice(dto.getPrice());
        dish.setName(dto.getName());
        dish.setDescription(dto.getDescription());
        Dish saved = dishRepository.save(dish);
        return saved;
    }
}
