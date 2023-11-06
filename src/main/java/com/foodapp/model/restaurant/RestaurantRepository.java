package com.foodapp.model.restaurant;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

    List<Restaurant> findAll();

    Optional<Restaurant> findById(Long id);

    @Query("SELECT r FROM Restaurant r JOIN r.dishes d WHERE d.id = :dishId")
    Optional<Restaurant> findRestaurantByDishId(@Param("dishId") Long dishId);

}
