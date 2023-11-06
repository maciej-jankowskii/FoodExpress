package com.foodapp.model.dish;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DishDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long restaurantId;
}
