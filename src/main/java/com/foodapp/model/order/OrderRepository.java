package com.foodapp.model.order;

import com.foodapp.model.dish.Dish;
import com.foodapp.model.restaurant.Restaurant;
import com.foodapp.model.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findOrderByUser(User user);
}
