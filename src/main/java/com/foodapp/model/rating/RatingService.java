package com.foodapp.model.rating;

import com.foodapp.model.order.Order;
import com.foodapp.model.order.OrderRepository;
import com.foodapp.model.restaurant.Restaurant;
import com.foodapp.model.restaurant.RestaurantRepository;
import com.foodapp.model.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RatingService {

    private final OrderRepository orderRepository;
    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final RestaurantRepository restaurantRepository;

    public RatingService(OrderRepository orderRepository, RatingRepository ratingRepository, RatingMapper ratingMapper, RestaurantRepository restaurantRepository) {
        this.orderRepository = orderRepository;
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public RatingDTO addRating(Long orderId, Rating rating){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NoSuchElementException("Order not found"));
        User user = order.getUser();
        Restaurant restaurant = order.getRestaurant();
        setInitialDataForRatingAndUserAndRestaurant(rating, order, user, restaurant);
        ratingRepository.save(rating);
        orderRepository.save(order);
        return ratingMapper.map(rating);
    }

    public void deleteRatingById(Long id){
        ratingRepository.deleteById(id);
    }

    /**
     Helper method for ratings
     */

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
}
