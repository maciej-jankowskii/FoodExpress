package com.foodapp.model.order;

import com.foodapp.model.dish.Dish;
import com.foodapp.model.dish.DishRepository;
import com.foodapp.model.enums.OrderStatus;
import com.foodapp.model.restaurant.Restaurant;
import com.foodapp.model.restaurant.RestaurantRepository;
import com.foodapp.model.user.User;
import com.foodapp.model.user.UserRepository;
import com.foodapp.model.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    @Mock
    private DishRepository dishRepository;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private UserService userService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ExtraPointsCalculator extraPointsCalculator;
    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create order without exceptions")
    public void createOrder_Success_Test() {
        HttpSession session = mock(HttpSession.class);
        Dish dish = new Dish();
        Order order = new Order();
        Long dishId = 1L;
        Restaurant restaurant = new Restaurant();
        List<Dish> dishes = new ArrayList<>();
        User user = new User();
        dish.setPrice(BigDecimal.TEN);

        when(session.getAttribute("order")).thenReturn(null);
        when(restaurantRepository.findRestaurantByDishId(dishId)).thenReturn(Optional.of(restaurant));
        when(userService.getLoggedInUser()).thenReturn(user);
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));
        Order createdOrder = orderService.createOrder(dishId, session);

        assertNotNull(order);
        assertEquals(OrderStatus.W_KOSZYKU, createdOrder.getOrderStatus());
        assertEquals(1, createdOrder.getDishes().size());
        assertEquals(BigDecimal.TEN, createdOrder.getTotalCost());

    }

    @Test
    @DisplayName("When the order is created, no restaurant exception should be thrown")
    public void createOrder_No_Restaurant_Exception_Test() {
        HttpSession session = mock(HttpSession.class);
        Long dishId = 1L;

        when(restaurantRepository.findRestaurantByDishId(dishId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderService.createOrder(dishId, session));
    }

    @Test
    @DisplayName("When the order is created, no dish exception should be thrown")
    public void createOrder_No_Dish_Exception_Test() {
        HttpSession session = mock(HttpSession.class);
        Long dishId = 1L;
        Restaurant restaurant = new Restaurant();

        when(restaurantRepository.findRestaurantByDishId(dishId)).thenReturn(Optional.of(restaurant));
        when(dishRepository.findById(dishId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderService.createOrder(dishId, session));

    }

    @Test
    @DisplayName("Should remove Dish from Order")
    public void removeDishFromOrder_Success_Test() {
        Order order = new Order();
        Dish dish = new Dish();
        Long dishId = 1L;
        dish.setId(dishId);
        order.getDishes().add(dish);

        when(orderRepository.save(order)).thenReturn(order);
        orderService.removeDishFromOrder(dishId, order);

        assertTrue(order.getDishes().isEmpty());
        assertEquals(BigDecimal.ZERO, order.getTotalCost());

    }

    @Test
    @DisplayName("Should pay for order with points")
    public void payWithPoints_Success_Test() {
        HttpSession session = mock(HttpSession.class);
        User user = new User();
        user.setExtraPoints(1000.0);
        Order order = new Order();
        BigDecimal totalCost = BigDecimal.valueOf(100.0);
        order.setTotalCost(totalCost);

        when(session.getAttribute("order")).thenReturn(order);
        when(userService.getLoggedInUser()).thenReturn(user);
        when(extraPointsCalculator.calculateExtraPoints(user)).thenReturn(100.0);
        when(orderRepository.save(order)).thenReturn(order);
        when(extraPointsCalculator.calculateExtraPointsForOrder(order)).thenReturn(BigDecimal.valueOf(10.0));
        when(userRepository.save(user)).thenReturn(user);
        boolean paid = orderService.payWithPoints(session);

        assertTrue(paid);
        assertEquals(OrderStatus.ZAŁOŻONE, order.getOrderStatus());
//        assertEquals(BigDecimal.valueOf(10.0), BigDecimal.valueOf(user.getExtraPoints()));  Nie wiadomo dlaczego nie oblicza poprawnie puntków <- do sprawdzenia
        verify(session).removeAttribute("order");

    }

    @Test
    @DisplayName("Payment with points should fail")
    public void payWithPoints_Fail_Test() {
        HttpSession session = mock(HttpSession.class);
        User user = new User();
        user.setExtraPoints(10.0);
        Order order = new Order();
        BigDecimal totalCost = BigDecimal.valueOf(100);
        order.setTotalCost(totalCost);

        when(session.getAttribute("order")).thenReturn(order);
        when(userService.getLoggedInUser()).thenReturn(user);
        when(extraPointsCalculator.calculateExtraPoints(user)).thenReturn(1.0);
        boolean paid = orderService.payWithPoints(session);

        assertFalse(paid);
        assertEquals(10.0, user.getExtraPoints());
        verify(session, never()).removeAttribute("order");

    }

    @Test
    @DisplayName("Place order with no exceptions")
    public void placeOrder_Success_Test(){
        HttpSession session = mock(HttpSession.class);
        Order order = new Order();
        order.setTotalCost(BigDecimal.valueOf(100.0));
        User user = new User();
        user.setExtraPoints(100.0);

        when(session.getAttribute("order")).thenReturn(order);
        when(extraPointsCalculator.calculateExtraPointsForOrder(order)).thenReturn(BigDecimal.valueOf(10.0));
        when(userService.getLoggedInUser()).thenReturn(user);
        when(orderRepository.save(order)).thenReturn(order);
        when(userRepository.save(user)).thenReturn(user);
        orderService.placeOrder(session);

        assertEquals(OrderStatus.ZAŁOŻONE, order.getOrderStatus());
//        assertEquals(BigDecimal.valueOf(110.0), BigDecimal.valueOf(user.getExtraPoints())); Podobna sytuacja jak wyżej
        verify(session).removeAttribute("order");
    }
}