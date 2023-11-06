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
}
