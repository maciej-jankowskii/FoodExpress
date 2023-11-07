package com.foodapp.model.order;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private Long userId;
    private Long restaurantId;
    private LocalDate orderDate;
    private BigDecimal totalCost;
    private String status;
    private Long ratingId;
    private Boolean rated;

}
