package com.foodapp.model.order;


import com.foodapp.model.enums.OrderStatus;
import com.foodapp.model.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findOrderByUser(User user);
    List<Order> findAllByOrderStatus(OrderStatus status);
    List<Order> findAllByOrderStatusAndUser(OrderStatus status, User user);
}
