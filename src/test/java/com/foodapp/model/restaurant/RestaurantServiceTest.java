package com.foodapp.model.restaurant;

import com.foodapp.model.address.Address;
import com.foodapp.model.address.AddressDTO;
import com.foodapp.model.address.AddressMapper;
import com.foodapp.model.address.AddressRepository;
import com.foodapp.model.enums.Cuisine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private AddressMapper addressMapper;
    @Mock
    private RestaurantMapper restaurantMapper;
    @InjectMocks
    private RestaurantService restaurantService;


    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should find Restaurant by Id")
    public void findById_Success_Test() {
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        Restaurant result = restaurantService.findById(restaurantId);

        assertNotNull(result);
        assertEquals(result.getId(), restaurant.getId());
    }

    @Test
    @DisplayName("An exception should be thrown when searching for a restaurant")
    public void findById_Exception_Test() {
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> restaurantService.findById(restaurantId));
    }

    @Test
    @DisplayName("Should find all restaurants")
    public void findAllRestaurantTest() {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant());
        restaurants.add(new Restaurant());

        when(restaurantRepository.findAll()).thenReturn(restaurants);
        List<Restaurant> result = restaurantService.findAllRestaurants();

        assertEquals(restaurants.size(), result.size());
    }

    @Test
    @DisplayName("Should delete restaurant")
    public void deleteRestaurantById() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurantRepository.save(restaurant);

        restaurantService.deleteRestaurantById(restaurant.getId());

        assertTrue(restaurantRepository.findById(restaurant.getId()).isEmpty());
    }

    @Test
    @DisplayName("Should add restaurant")
    public void addNewRestaurant() {
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setName("New Restaurant");
        restaurantDTO.setTypeOfCuisine("WŁOSKA");
        Restaurant restaurant = new Restaurant();

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCity("City");
        addressDTO.setStreet("Street");
        addressDTO.setHomeNo("123");
        addressDTO.setFlatNo("45");
        addressDTO.setPostalCode("12345");
        Address address = new Address();

        when(addressMapper.map(addressDTO)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(address);
        when(restaurantMapper.map(restaurantDTO)).thenReturn(restaurant);
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);
        when(restaurantMapper.map(restaurant)).thenReturn(restaurantDTO);

        RestaurantDTO result = restaurantService.addNewRestaurant(restaurantDTO, addressDTO);

        assertNotNull(result);
        assertEquals(restaurantDTO.getName(), result.getName());

        verify(addressRepository, times(1)).save(any(Address.class));
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }
}