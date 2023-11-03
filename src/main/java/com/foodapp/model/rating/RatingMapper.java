package com.foodapp.model.rating;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RatingMapper {
    RatingMapper INSTANCE = Mappers.getMapper(RatingMapper.class);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "restaurantId", source = "restaurant.id")
    RatingDTO ratingToDTO(Rating rating);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "restaurant.id", source = "restaurantId")
    Rating dtoToRating(RatingDTO ratingDTO);
}
