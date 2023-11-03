package com.foodapp.model.dish;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DishMapper {
    DishMapper INSTANCE = Mappers.getMapper(DishMapper.class);
    DishDTO dishToDishDTO(Dish dish);
    Dish dishDTOToDish(DishDTO dishDTO);
}
