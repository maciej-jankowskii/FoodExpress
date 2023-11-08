package com.foodapp.model.restaurant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;
    @InjectMocks
    private RestaurantService restaurantService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should find Restaurant by Id")
    public void findById_Success_Test(){
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        Restaurant result = restaurantService.findById(restaurantId);

        assertNotNull(result);
        assertEquals(result.getId(), restaurant.getId());
    }

    @Test
    @DisplayName("An exception should be thrown when searching for a restaurant")
    public void findById_Exception_Test(){
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> restaurantService.findById(restaurantId));
    }

    @Test
    @DisplayName("Should find all restaurants")
    public void findAllRestaurantTest(){
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant());
        restaurants.add(new Restaurant());

        when(restaurantRepository.findAll()).thenReturn(restaurants);
        List<Restaurant> result = restaurantService.findAllRestaurants();

        assertEquals(restaurants.size(), result.size());
    }

}