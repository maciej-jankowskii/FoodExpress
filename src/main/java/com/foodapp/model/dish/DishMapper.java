package com.foodapp.model.dish;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
public class DishMapper {
    public DishDTO map(Dish dish){
        DishDTO dto = new DishDTO();
        dto.setName(dish.getName());
        dto.setDescription(dish.getDescription());
        dto.setPrice(dish.getPrice());
        return dto;
    }
    public Dish map(DishDTO dishDTO){
        Dish dish = new Dish();
        dish.setName(dishDTO.getName());
        dish.setDescription(dishDTO.getDescription());
        dish.setPrice(dishDTO.getPrice());
        return dish;
    }
}
