package com.foodapp.model.order;

import com.foodapp.model.enums.OrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Named("orderStatusToString")
    default String orderStatusToString(OrderStatus orderStatus) {
        return orderStatus.toString();
    }

    @Named("stringToOrderStatus")
    default OrderStatus stringToOrderStatus(String value) {
        return OrderStatus.valueOf(value);
    }

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "restaurantId", source = "restaurant.id")
    @Mapping(target = "orderStatus", source = "orderStatus", qualifiedByName = "orderStatusToString")
    OrderDTO orderToDTO(Order order);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "restaurant.id", source = "restaurantId")
    @Mapping(target = "orderStatus", source = "orderStatus", qualifiedByName = "stringToOrderStatus")
    Order dtoToOrder(OrderDTO orderDTO);
}
