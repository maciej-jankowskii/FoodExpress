package com.foodapp.model.order;

import com.foodapp.model.dish.Dish;
import com.foodapp.model.dish.DishMapper;
import com.foodapp.model.dish.DishRepository;
import com.foodapp.model.restaurant.Restaurant;
import com.foodapp.model.restaurant.RestaurantRepository;
import com.foodapp.model.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {
    private final static Double EXTRA_POINT_TO_PLN = 0.1;
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserService userService;

    public OrderService(DishRepository dishRepository, RestaurantRepository restaurantRepository, UserService userService) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
    }
    @Transactional
    public Order createOrder(Long dishId, HttpSession session){
        Order order = (Order) session.getAttribute("order");

        Restaurant restaurant = restaurantRepository.findRestaurantByDishId(dishId).orElseThrow(() -> new NoSuchElementException("Restaurant not found"));

        if (order == null){
            order = new Order();
            List<Dish> dishes = new ArrayList<>();
            order.setDishes(dishes);
            order.setRestaurant(restaurant);
            setInitialData(order, dishes);
            addDishToOrder(dishId, dishes);
            session.setAttribute("order", order);
            order.setTotalCost(calculateTotalCost(order.getDishes()));
        } else {
            addDishToOrder(dishId, order.getDishes());
        }
        return order;
    }

    public void addDishToOrder(Long dishId, List<Dish> dishes ) {
        Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new NoSuchElementException("Dish not found"));
        dishes.add(dish);
    }

    private void setInitialData(Order order, List<Dish> dishes) {
        order.setUser(userService.getLoggedInUser());
        order.setOrderDate(LocalDate.now());
        order.setTotalCost(calculateTotalCost(dishes));
    }

    public BigDecimal calculateTotalCost(List<Dish> orderDishes){
        BigDecimal totalCost = BigDecimal.ZERO;
        for (Dish orderDish : orderDishes) {
            totalCost = totalCost.add(orderDish.getPrice());
        }
        return totalCost;
    }


    private Double calculateExtraPoints(Double extraPoints){
        return EXTRA_POINT_TO_PLN * extraPoints;

    }

    private BigDecimal calculateExtraPointsForOrder(List<Dish> orderDishes){
        BigDecimal totalCost = calculateTotalCost(orderDishes);
        return totalCost.multiply(BigDecimal.valueOf(EXTRA_POINT_TO_PLN));
    }

}
