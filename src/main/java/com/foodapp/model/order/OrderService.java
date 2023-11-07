package com.foodapp.model.order;

import com.foodapp.model.dish.Dish;
import com.foodapp.model.dish.DishRepository;
import com.foodapp.model.enums.OrderStatus;
import com.foodapp.model.rating.Rating;
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
    private final RatingRepository ratingRepository;

    public OrderService(DishRepository dishRepository, RestaurantRepository restaurantRepository, UserService userService, OrderRepository orderRepository, UserRepository userRepository, RatingRepository repository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.ratingRepository = repository;
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
            order.setDishes(dishes);
            order.setRestaurant(restaurant);
            setInitialDataForOrder(order, dishes);
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

    private void setInitialDataForOrder(Order order, List<Dish> dishes) {
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
    public boolean payWithPoints(HttpSession session) {
        Order order = (Order) session.getAttribute("order");
        BigDecimal totalCost = order.getTotalCost();
        User user = userService.getLoggedInUser();
        Double points = calculateExtraPoints(user);

        if (totalCost.doubleValue() < points) {
            order.setOrderStatus(OrderStatus.ZAŁOŻONE);
            orderRepository.save(order);
            user.setExtraPoints(user.getExtraPoints() - totalCost.doubleValue());
            BigDecimal additionalPoints = calculateExtraPointsForOrder(order);
            user.setExtraPoints(user.getExtraPoints() + additionalPoints.doubleValue());
            userRepository.save(user);
            session.removeAttribute("order");
            return true;
        }
        return false;
    }

    public void placeOrder(HttpSession session) {
        Order order = (Order) session.getAttribute("order");
        BigDecimal additionalPoints = calculateExtraPointsForOrder(order);
        User user = userService.getLoggedInUser();
        user.setExtraPoints(user.getExtraPoints() + additionalPoints.doubleValue());
        order.setOrderStatus(OrderStatus.ZAŁOŻONE);
        orderRepository.save(order);
        userRepository.save(user);
        session.removeAttribute("order");
    }

    public void addRating(Long orderId, Rating rating){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NoSuchElementException("Order not found"));
        User user = order.getUser();
        Restaurant restaurant = order.getRestaurant();
        setInitialDataForRatingAndUserAndRestaurant(rating, order, user, restaurant);
        ratingRepository.save(rating);
        orderRepository.save(order);
    }

    private static void setInitialDataForRatingAndUserAndRestaurant(Rating rating, Order order, User user, Restaurant restaurant) {
        rating.setUser(user);
        rating.setRestaurant(restaurant);
        rating.setDateAdded(LocalDate.now());
        user.getRatings().add(rating);
        restaurant.getRatings().add(rating);
        order.setRating(rating);
        order.setRated(true);
    }

    public void calculateAndSetRatings(List<Restaurant> restaurants){
        for (Restaurant restaurant : restaurants) {
            Double averageRating = calculateRatings(restaurant);
            restaurant.setAverageRating(averageRating);
            restaurantRepository.save(restaurant);
        }

    }
    private Double calculateRatings(Restaurant restaurant) {
        List<Rating> ratings = restaurant.getRatings();
        if (ratings.isEmpty()) {
            return 0.0;
        }
        Double sum = 0.0;
        for (Rating rating : ratings) {
            sum += rating.getValue();
        }
        return sum / ratings.size();
    }


    public Double calculateExtraPoints(User user){
        return EXTRA_POINT_TO_PLN * user.getExtraPoints();

    }

    public BigDecimal calculateExtraPointsForOrder(Order order){
        BigDecimal orderTotalCost = calculateTotalCost(order.getDishes());
        return orderTotalCost.multiply(BigDecimal.valueOf(EXTRA_POINT_TO_PLN));
    }
}
