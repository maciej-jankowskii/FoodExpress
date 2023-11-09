package com.foodapp.controller;

import com.foodapp.model.dish.Dish;
import com.foodapp.model.dish.DishDTO;
import com.foodapp.model.dish.DishService;
import com.foodapp.model.restaurant.Restaurant;
import com.foodapp.model.restaurant.RestaurantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RestaurantService restaurantService;
    private final DishService dishService;

    public AdminController(RestaurantService restaurantService, DishService dishService) {
        this.restaurantService = restaurantService;
        this.dishService = dishService;
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

    @GetMapping("/edit-dish/{dishId}")
    public String editDishForm(@PathVariable Long dishId, Model model){
        try {
            Dish dish = dishService.findDishById(dishId);
            model.addAttribute("dish", dish);
            return "admin/dish-edit";
        } catch (NoSuchElementException e){
            return "redirect:/error/restaurant-error";
        }
    }

    @PostMapping("/edit-dish/{dishId}")
    public String editDishById(@PathVariable Long dishId, @ModelAttribute("dish")DishDTO dto){
        try {
            dto.setId(dishId);
            dishService.editDishById(dto);
            return "redirect:/admin/restaurants";
        }catch (NoSuchElementException e){
            return "redirect:/error/restaurant-error";
        }
    }

    @PostMapping("/delete-dish/{dishId}")
    public String deleteDishById(@PathVariable("dishId") Long dishId){
        dishService.deleteDishById(dishId);
        return "redirect:/admin/edit-menu/" + dishId;
    }

    @GetMapping("/add-dish/{restaurantId}")
    public String addDishForm(@PathVariable Long restaurantId, Model model) {
        Dish dish = new Dish();
        model.addAttribute("dish", dish);
        model.addAttribute("restaurantId", restaurantId);
        return "admin/add-dish";
    }

    @PostMapping("/add-dish/{restaurantId}")
    public String addNewDish(@PathVariable Long restaurantId, @ModelAttribute DishDTO dto){
        try {
            dishService.addNewDish(restaurantId,dto);
            return "redirect:/admin/edit-menu/" + restaurantId;
        }catch (NoSuchElementException e){
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
