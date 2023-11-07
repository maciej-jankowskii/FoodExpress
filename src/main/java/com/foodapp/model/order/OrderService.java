package com.foodapp.model.order;

import com.foodapp.model.dish.Dish;
import com.foodapp.model.dish.DishRepository;
import com.foodapp.model.enums.OrderStatus;
import com.foodapp.model.rating.Rating;
import com.foodapp.model.rating.RatingDTO;
import com.foodapp.model.rating.RatingMapper;
import com.foodapp.model.rating.RatingRepository;
import com.foodapp.model.restaurant.Restaurant;
import com.foodapp.model.restaurant.RestaurantRepository;
import com.foodapp.model.user.User;
import com.foodapp.model.user.UserRepository;
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
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(DishRepository dishRepository, RestaurantRepository restaurantRepository, UserService userService,
                        OrderRepository orderRepository, UserRepository userRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public List<Order> findAllByStatus(OrderStatus status, User user){
        return orderRepository.findAllByOrderStatusAndUser(status, user);
    }
    @Transactional
    public Order createOrder(Long dishId, HttpSession session){
        Order order = (Order) session.getAttribute("order");
        Restaurant restaurant = restaurantRepository.findRestaurantByDishId(dishId).orElseThrow(() -> new NoSuchElementException("Restaurant not found"));
        if (order == null){
            order = new Order();
            List<Dish> dishes = new ArrayList<>();
            setInitialDataForNewOrder(order, restaurant, dishes);
            addDishToOrder(dishId, dishes);
            session.setAttribute("order", order);
            order.setTotalCost(calculateTotalCost(order.getDishes()));
        } else {
            addDishToOrder(dishId, order.getDishes());
        }
        return order;
    }
    @Transactional
    public void removeDishFromOrder(Long dishId, Order order) {
        Dish dishToRemove = order.getDishes().stream()
                .filter(dish -> dish.getId().equals(dishId))
                .findFirst()
                .orElse(null);
        if (dishToRemove != null){
            order.getDishes().remove(dishToRemove);
            order.setTotalCost(calculateTotalCost(order.getDishes()));
            orderRepository.save(order);
        }
    }
    @Transactional
    public boolean payWithPoints(HttpSession session) {
        Order order = (Order) session.getAttribute("order");
        BigDecimal totalCost = order.getTotalCost();
        User user = userService.getLoggedInUser();
        Double points = calculateExtraPoints(user);

        if (totalCost.doubleValue() < points) {
            order.setOrderStatus(OrderStatus.ZAŁOŻONE);
            orderRepository.save(order);
            calculateNewValueOfExtraPoints(order, totalCost, user);
            userRepository.save(user);
            session.removeAttribute("order");
            return true;
        }
        return false;
    }

    @Transactional
    public void placeOrder(HttpSession session) {
        Order order = (Order) session.getAttribute("order");
        BigDecimal additionalPoints = calculateExtraPointsForOrder(order);
        User user = userService.getLoggedInUser();
        setDataForPlacedOrderAndSave(order, additionalPoints, user);
        session.removeAttribute("order");
    }


    /**
    Helper method for placed Order
     */

    private void setDataForPlacedOrderAndSave(Order order, BigDecimal additionalPoints, User user) {
        user.setExtraPoints(user.getExtraPoints() + additionalPoints.doubleValue());
        order.setOrderStatus(OrderStatus.ZAŁOŻONE);
        orderRepository.save(order);
        userRepository.save(user);
    }

    /**
    Helper methods to calculate User ExtraPoints
     */

    public Double calculateExtraPoints(User user){
        return EXTRA_POINT_TO_PLN * user.getExtraPoints();
    }

    public BigDecimal calculateExtraPointsForOrder(Order order){
        BigDecimal orderTotalCost = calculateTotalCost(order.getDishes());
        return orderTotalCost.multiply(BigDecimal.valueOf(EXTRA_POINT_TO_PLN));
    }

    private void calculateNewValueOfExtraPoints(Order order, BigDecimal totalCost, User user) {
        user.setExtraPoints(user.getExtraPoints() - totalCost.doubleValue());
        BigDecimal additionalPoints = calculateExtraPointsForOrder(order);
        user.setExtraPoints(user.getExtraPoints() + additionalPoints.doubleValue());
    }

    /**
     Helper methods for creating an order and adding meals to it
     */

    public void addDishToOrder(Long dishId, List<Dish> dishes ) {
        Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new NoSuchElementException("Dish not found"));
        dishes.add(dish);
    }

    private void setInitialDataForNewOrder(Order order, Restaurant restaurant, List<Dish> dishes) {
        order.setDishes(dishes);
        order.setRestaurant(restaurant);
        order.setUser(userService.getLoggedInUser());
        order.setOrderDate(LocalDate.now());
        order.setTotalCost(calculateTotalCost(dishes));
        order.setOrderStatus(OrderStatus.W_KOSZYKU);
    }
    public BigDecimal calculateTotalCost(List<Dish> orderDishes){
        BigDecimal totalCost = BigDecimal.ZERO;
        for (Dish orderDish : orderDishes) {
            totalCost = totalCost.add(orderDish.getPrice());
        }
        return totalCost;
    }
}
