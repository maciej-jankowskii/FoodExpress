package com.foodapp.model.order;

import com.foodapp.model.user.User;

import java.math.BigDecimal;

public interface ExtraPointsCalculator {
     Double calculateExtraPoints(User user);
     BigDecimal calculateExtraPointsForOrder(Order order);
}
