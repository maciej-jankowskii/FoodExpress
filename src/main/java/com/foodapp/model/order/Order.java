package com.foodapp.model.order;

import com.foodapp.model.dish.Dish;
import com.foodapp.model.enums.OrderStatus;
import com.foodapp.model.restaurant.Restaurant;
import com.foodapp.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @OneToMany
    @JoinColumn(name = "dish_id")
    private List<Dish> dishes = new ArrayList<>();
    private BigDecimal totalCost;
    private OrderStatus orderStatus;
    private LocalDate orderDate;

}
