package com.foodapp.model.order;

import com.foodapp.model.restaurant.Restaurant;
import com.foodapp.model.restaurant.RestaurantRepository;
import com.foodapp.model.user.User;
import com.foodapp.model.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public OrderMapper(UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public OrderDTO map(Order order){
        OrderDTO dto = new OrderDTO();
        dto.setOrderDate(order.getOrderDate());
        dto.setUserId(order.getUser().getId());
        dto.setRestaurantId(order.getRestaurant().getId());
        dto.setTotalCost(order.getTotalCost());
        return dto;
    }

    public Order map(OrderDTO dto){
        Order order = new Order();
        order.setOrderDate(dto.getOrderDate());
        User user = userRepository.findById(dto.getUserId()).orElse(null);
        order.setUser(user);
        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId()).orElse(null);
        order.setRestaurant(restaurant);
        order.setTotalCost(dto.getTotalCost());
        return order;
    }

}
