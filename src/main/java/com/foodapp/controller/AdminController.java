package com.foodapp.controller;

import com.foodapp.model.restaurant.Restaurant;
import com.foodapp.model.restaurant.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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




    /**
     Handling requests related to ratings
     */






    /**
     Handling requests related to users
     */
}
