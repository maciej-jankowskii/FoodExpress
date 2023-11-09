package com.foodapp.controller;

import com.foodapp.exception.MinimumOrderValueException;
import com.foodapp.model.dish.Dish;
import com.foodapp.model.enums.OrderStatus;
import com.foodapp.model.order.Order;
import com.foodapp.model.order.OrderRepository;
import com.foodapp.model.order.OrderService;
import com.foodapp.model.rating.Rating;
import com.foodapp.model.rating.RatingService;
import com.foodapp.model.restaurant.Restaurant;
import com.foodapp.model.restaurant.RestaurantService;
import com.foodapp.model.user.User;
import com.foodapp.model.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class ClientOrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final RestaurantService restaurantService;
    private final OrderRepository orderRepository;
    private final RatingService ratingService;


    public ClientOrderController(OrderService orderService, UserService userService,
                                 RestaurantService restaurantService, OrderRepository orderRepository, RatingService ratingService) {
        this.orderService = orderService;
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.orderRepository = orderRepository;
        this.ratingService = ratingService;
    }

    /**
     Handling order and payments requests
     */

    @GetMapping("/order")
    public String orderForm(Model model) {
        List<Restaurant> restaurants = restaurantService.findAllRestaurants();
        ratingService.calculateAndSetRatings(restaurants);
        model.addAttribute("restaurants", restaurants);
        return "/order/restaurants";
    }

    @GetMapping("/restaurant/menu/{restaurantId}")
    public String restaurantMenuForm(@PathVariable Long restaurantId, Model model) {
        try {
            Restaurant restaurant = restaurantService.findById(restaurantId);
            List<Dish> menu = restaurant.getDishes();
            model.addAttribute("restaurant", restaurant);
            model.addAttribute("menu", menu);
            model.addAttribute("restaurantId", restaurantId);
            return "/order/restaurant-menu";
        } catch (NoSuchElementException e) {
            return "redirect:/restaurant-error";
        }
    }

    @PostMapping("/add-to-cart")
    public String createOrderAndAddDish(@RequestParam("dishId") Long dishId, @RequestParam("restaurantId") Long restaurantId, HttpSession session, Model model) {
        try {
            Order order = (Order) session.getAttribute("order");

            if (order == null) {
                order = orderService.createOrder(dishId, session);
            } else {
                if (!orderService.isDishFromTheSameRestaurant(dishId, order)){
                    return "redirect:/different-restaurant-error";
                }
                orderService.addDishToOrder(dishId, order.getDishes());
                order.setTotalCost(orderService.calculateTotalCost(order.getDishes()));
                orderRepository.save(order);
            }
            session.setAttribute("order", order);
            return "redirect:/restaurant/menu/" + restaurantId;
        } catch (NoSuchElementException e) {
            return "redirect:/restaurant-error";
        }
    }

    @GetMapping("/order-summary")
    public String summaryFrom(Model model, HttpSession session){
        Order order = (Order) session.getAttribute("order");
        User user = userService.getLoggedInUser();

        if (order != null){
            BigDecimal extraPointsForOrder = orderService.calculateExtraPointsForOrder(order);
            Double availableExtraPoints = orderService.calculateExtraPoints(user);
            model.addAttribute("order", order);
            model.addAttribute("user", user);
            model.addAttribute("extraPointsForOrder", extraPointsForOrder);
            model.addAttribute("availableExtraPoints", availableExtraPoints);
        }
        return "order/summary";
    }

    @PostMapping("/remove-from-cart/{dishId}")
    public String removeDishFromCart(@PathVariable("dishId") Long dishId, HttpSession session){
        Order order = (Order) session.getAttribute("order");
        if(order !=null){
            orderService.removeDishFromOrder(dishId, order);
            session.setAttribute("order", order);
            orderRepository.save(order);
        }
        return "redirect:/order-summary";
    }

    @GetMapping("/pay-with-points")
    public String payWithPoints(HttpSession session){
        Order order = (Order) session.getAttribute("order");
        if (orderService.payWithPoints(session)) {
            return "order/payment-success";
        } else {
            return "redirect:/not-enough-points";
        }
    }

    @GetMapping("/place-order")
    public String placeOrder(HttpSession session){
        try {
            Order order = (Order) session.getAttribute("order");
            orderService.placeOrder(session);
            return "order/payment-success";
        } catch (MinimumOrderValueException e){
            return "redirect:/minimum-value-error";
        }

    }

    /**
     Handling history of orders and rating requests
     */

    @GetMapping("/my-orders")
    public String myOrdersForm(Model model){
        User user = userService.getLoggedInUser();
        List<Order> allOrders = orderService.findAllByStatus(OrderStatus.ZAŁOŻONE, user);
        model.addAttribute("allOrders", allOrders);
        return "order/my-orders";
    }

    @PostMapping("/add-rating")
    public String addRating(@RequestParam("orderId") Long orderId, Rating rating){
        try {
            ratingService.addRating(orderId, rating);
            return "redirect:/my-orders";
        }catch (NoSuchElementException e){
            return "redirect:/order-error";
        }
    }

    /**
     Handling errors
     */

    @GetMapping("/restaurant-error")
    public String restaurantErrorForm(){
        return "error/restaurant-error";
    }
    @GetMapping("/not-enough-points")
    public String notEnoughPointsErrorForm(){
        return "error/not-enough-points";
    }
    @GetMapping("/order-error")
    public String orderErrorForm(){
        return "error/order-error";
    }
    @GetMapping("/different-restaurant-error")
    public String diffRestaurantError(){
        return "error/different-restaurant-error";
    }

    @GetMapping("/minimum-value-error")
    public String minimumValueError(){
        return "error/minimum-value-error";
    }
}


