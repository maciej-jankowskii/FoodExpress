package com.foodapp.model.dish;

import com.foodapp.model.restaurant.Restaurant;
import com.foodapp.model.restaurant.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class DishServiceTest {
    @Mock private DishRepository dishRepository;
    @Mock private DishMapper dishMapper;
    @Mock private RestaurantRepository restaurantRepository;
    @InjectMocks private DishService dishService;
    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }
    @DisplayName("Should edit without any problems")
    @Test
    public void editDish_Success_Test(){
        DishDTO dto = new DishDTO();
        Dish dish = new Dish();
        dto.setId(1L);
        dto.setName("New name");
        dish.setId(1L);
        dish.setName("Old name");

        when(dishRepository.findById(1L)).thenReturn(Optional.of(dish));
        when(dishRepository.save(any(Dish.class))).thenReturn(dish);
        when(dishMapper.map(dish)).thenReturn(dto);

        DishDTO result = dishService.editDishById(dto);

        assertNotNull(result);
        assertEquals(result.getName(), dto.getName());
    }
    @Test
    @DisplayName("Should add new dish without exceptions")
    void testAddNewDish_Success_Test() {
        Long restaurantId = 1L;
        DishDTO dishDTO = new DishDTO();
        dishDTO.setName("New Dish");
        dishDTO.setPrice(BigDecimal.TEN);
        dishDTO.setDescription("Delicious dish");
        Dish dish = new Dish();
        Restaurant restaurant = new Restaurant();
        dishDTO.setRestaurantId(restaurantId);


        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(dishMapper.map(dishDTO)).thenReturn(dish);
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);
        when(dishRepository.save(dish)).thenReturn(dish);
        when(dishMapper.map(dish)).thenReturn(dishDTO);

        DishDTO result = dishService.addNewDish(restaurantId, dishDTO);

        assertNotNull(result);
        assertEquals(dishDTO.getName(), result.getName());
        assertEquals(dishDTO.getPrice(), result.getPrice());
        assertEquals(dishDTO.getDescription(), result.getDescription());
        assertEquals(restaurantId, result.getRestaurantId());
    }

    @Test
    @DisplayName("Should throw exception when dish not found")
    void testAddNewDishRestaurantNotFound() {
        Long restaurantId = 1L;
        DishDTO dishDTO = new DishDTO();

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> dishService.addNewDish(restaurantId, dishDTO));
    }




}