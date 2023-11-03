package com.foodapp.model.order;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private Long userId;
    private Long restaurantId;
    private String orderStatus;
    private LocalDate orderDate;
    private Double totalCost;
    private String comments;
}
