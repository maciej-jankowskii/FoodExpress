package com.foodapp.model.rating;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RatingDTO {
    private Long id;
    private Integer value;
    private String description;
    private LocalDate dateAdded;
    private Long userId;
    private Long restaurantId;
}
