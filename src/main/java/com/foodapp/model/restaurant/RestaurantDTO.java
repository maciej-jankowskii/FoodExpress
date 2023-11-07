package com.foodapp.model.restaurant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantDTO {
    private Long id;
    private String name;
    private Long addressId;
    private String typeOfCuisine;
    private Double averageRating;
}
