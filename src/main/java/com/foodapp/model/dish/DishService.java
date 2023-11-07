package com.foodapp.model.dish;

import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DishService {

    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public Dish findDishById(Long id){
        return dishRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Dish not found"));
    }
}
