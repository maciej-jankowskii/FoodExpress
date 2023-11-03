package com.foodapp.model.restaurant;

import com.foodapp.model.enums.Cuisine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RestaurantMapper {
    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    @Named("cuisineToString")
    default String cuisineToString(Cuisine cuisine) {
        return cuisine.toString();
    }

    @Named("stringToCuisine")
    default Cuisine stringToCuisine(String value) {
        return Cuisine.valueOf(value);
    }

    @Mapping(target = "addressId", source = "address.id")
    @Mapping(target = "typeOfCuisine", source = "typeOfCuisine", qualifiedByName = "cuisineToString")
    RestaurantDTO restaurantToDTO(Restaurant restaurant);

    @Mapping(target = "address.id", source = "addressId")
    @Mapping(target = "typeOfCuisine", source = "typeOfCuisine", qualifiedByName = "stringToCuisine")
    Restaurant dtoToRestaurant(RestaurantDTO restaurantDTO);
}
