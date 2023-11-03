package com.foodapp.model.dish;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
}
