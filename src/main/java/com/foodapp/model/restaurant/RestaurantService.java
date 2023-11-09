package com.foodapp.model.restaurant;

import com.foodapp.model.address.Address;
import com.foodapp.model.address.AddressDTO;
import com.foodapp.model.address.AddressMapper;
import com.foodapp.model.address.AddressRepository;
import com.foodapp.model.enums.Cuisine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;
    private final RestaurantMapper restaurantMapper;
    private final AddressMapper addressMapper;

    public RestaurantService(RestaurantRepository restaurantRepository, AddressRepository addressRepository, RestaurantMapper restaurantMapper, AddressMapper addressMapper) {
        this.restaurantRepository = restaurantRepository;
        this.addressRepository = addressRepository;
        this.restaurantMapper = restaurantMapper;
        this.addressMapper = addressMapper;
    }

    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Restaurant not found"));
    }

    public Restaurant findByDishId(Long dishId) {
        return restaurantRepository.findRestaurantByDishId(dishId).orElseThrow();
    }

    public List<Restaurant> findAllRestaurants() {
        return restaurantRepository.findAll();
    }
    public void deleteRestaurantById(Long id){
        restaurantRepository.deleteById(id);
    }
    @Transactional
    public RestaurantDTO addNewRestaurant(RestaurantDTO dto, AddressDTO addressDTO){
        Address address = createNewAddress(addressDTO);
        Address savedAddress = addressRepository.save(address);
        Restaurant restaurant = createNewRestaurant(dto, savedAddress);
        Restaurant saved = restaurantRepository.save(restaurant);
        return restaurantMapper.map(saved);
    }

    private Restaurant createNewRestaurant(RestaurantDTO dto, Address address) {
        Restaurant restaurant = restaurantMapper.map(dto);
        restaurant.setName(dto.getName());
        restaurant.setAddress(address);
        restaurant.setTypeOfCuisine(Cuisine.valueOf(dto.getTypeOfCuisine()));
        restaurant.setAverageRating(0.0);
        return restaurant;
    }

    private Address createNewAddress(AddressDTO addressDTO) {
        Address address = addressMapper.map(addressDTO);
        address.setCity(addressDTO.getCity());
        address.setStreet(addressDTO.getStreet());
        address.setHomeNo(addressDTO.getHomeNo());
        address.setFlatNo(addressDTO.getFlatNo());
        address.setPostalCode(addressDTO.getPostalCode());
        return address;
    }
}
