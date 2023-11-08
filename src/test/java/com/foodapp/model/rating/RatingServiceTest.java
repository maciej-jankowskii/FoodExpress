package com.foodapp.model.rating;

import com.foodapp.model.order.Order;
import com.foodapp.model.order.OrderRepository;
import com.foodapp.model.restaurant.Restaurant;
import com.foodapp.model.restaurant.RestaurantRepository;
import com.foodapp.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class RatingServiceTest {

    @Mock private OrderRepository orderRepository;
    @Mock private RatingRepository ratingRepository;
    @Mock private RatingMapper ratingMapper;
    @Mock private RestaurantRepository restaurantRepository;
    @InjectMocks private RatingService ratingService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should add rating without exception")
    public void addRating_Success_Test(){
        Long orderId = 1L;
        Rating rating = new Rating();
        RatingDTO dto = new RatingDTO();
        Order order = new Order();
        User user = new User();
        Restaurant restaurant = new Restaurant();
        order.setUser(user);
        order.setRestaurant(restaurant);
        LocalDate currentDate = LocalDate.now();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(ratingRepository.save(rating)).thenReturn(rating);
        when(orderRepository.save(order)).thenReturn(order);
        when(ratingMapper.map(rating)).thenReturn(dto);

        RatingDTO result = ratingService.addRating(orderId, rating);

        assertNotNull(result);
        assertTrue(order.getRated());
        assertEquals(rating, order.getRating());
        assertEquals(user, rating.getUser());
        assertEquals(restaurant, rating.getRestaurant());
        assertEquals(currentDate, rating.getDateAdded());

    }

    @Test
    @DisplayName("When rating is added an exception should be thrown")
    public void addRating_Exception_Test(){
        Long orderId = 1L;
        Rating rating = new Rating();

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> ratingService.addRating(orderId, rating));

    }



}