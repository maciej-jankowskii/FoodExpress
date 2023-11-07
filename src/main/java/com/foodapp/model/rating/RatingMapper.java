package com.foodapp.model.rating;

import org.springframework.stereotype.Service;


@Service
public class RatingMapper {
    public RatingDTO map(Rating rating){
        RatingDTO dto = new RatingDTO();
        dto.setUserId(rating.getUser().getId());
        dto.setRestaurantId(rating.getRestaurant().getId());
        dto.setValue(rating.getValue());
        dto.setDateAdded(rating.getDateAdded());
        dto.setDescription(rating.getDescription());
        return dto;
    }


}
