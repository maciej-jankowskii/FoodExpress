package com.foodapp.controller;

import com.foodapp.model.dish.Dish;
import com.foodapp.model.restaurant.Restaurant;
import com.foodapp.model.restaurant.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RestaurantService restaurantService;

    public AdminController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    /**
     Handling requests related to restaurants
     */

    @GetMapping("/restaurants")
    public String allRestaurantsForm(Model model) {
        List<Restaurant> restaurants = restaurantService.findAllRestaurants();
        model.addAttribute("restaurants", restaurants);
        return "admin/restaurants";
    }

    @PostMapping("/delete-restaurant/{id}")
    public String deleteRestaurant(@PathVariable("id") Long id){
        restaurantService.deleteRestaurantById(id);
        return "redirect:/admin/restaurants";
    }

    @GetMapping("/edit-menu/{id}")
    public String editMenuForm(@PathVariable("id") Long id, Model model){
        try {
            Restaurant restaurant = restaurantService.findById(id);
            List<Dish> dishes = restaurant.getDishes();
            model.addAttribute("restaurant", restaurant);
            model.addAttribute("dishes", dishes);
            return "admin/restaurant-menu";
        } catch (NoSuchElementException e){
            return "redirect:/error/restaurant-error";
        }
    }




    /**
     Handling requests related to ratings
     */






    /**
     Handling requests related to users
     */
}
