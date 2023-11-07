package com.foodapp.model.order;

import com.foodapp.model.enums.OrderStatus;
import com.foodapp.model.rating.Rating;
import com.foodapp.model.rating.RatingRepository;
import com.foodapp.model.restaurant.Restaurant;
import com.foodapp.model.restaurant.RestaurantRepository;
import com.foodapp.model.user.User;
import com.foodapp.model.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final RatingRepository ratingRepository;

    public OrderMapper(UserRepository userRepository, RestaurantRepository restaurantRepository, RatingRepository ratingRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.ratingRepository = ratingRepository;
    }

    public OrderDTO map(Order order){
        OrderDTO dto = new OrderDTO();
        dto.setUserId(order.getUser().getId());
        dto.setRestaurantId(order.getRestaurant().getId());
        dto.setTotalCost(order.getTotalCost());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(String.valueOf(order.getOrderStatus()));
        dto.setRatingId(order.getRating().getId());
        dto.setRated(order.getRated());
        return dto;
    }

    public Order map(OrderDTO dto){
        Order order = new Order();
        User user = userRepository.findById(dto.getUserId()).orElse(null);
        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId()).orElse(null);
        Rating rating = ratingRepository.findById(dto.getRatingId()).orElse(null);

        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setTotalCost(dto.getTotalCost());
        order.setOrderDate(dto.getOrderDate());
        order.setOrderStatus(OrderStatus.valueOf(dto.getStatus()));
        order.setRating(rating);
        order.setRated(dto.getRated());
        return order;
    }

}
