package com.foodapp.controller;

import com.foodapp.model.dish.Dish;
import com.foodapp.model.dish.DishService;
import com.foodapp.model.order.Order;
import com.foodapp.model.order.OrderRepository;
import com.foodapp.model.order.OrderService;
import com.foodapp.model.restaurant.Restaurant;
import com.foodapp.model.restaurant.RestaurantService;
import com.foodapp.model.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class ClientOrderController {

    private final OrderService orderService;
    private final DishService dishService;
    private final UserService userService;
    private final RestaurantService restaurantService;
    private final OrderRepository orderRepository;


    public ClientOrderController(OrderService orderService, DishService dishService, UserService userService, RestaurantService restaurantService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.dishService = dishService;
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/order")
    public String orderForm(Model model) {
        List<Restaurant> restaurants = restaurantService.findAllRestaurants();
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
}


