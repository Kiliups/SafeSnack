package com.safesnack.backend.repository;

import com.safesnack.backend.model.RestaurantRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRestaurantRatingRepository extends JpaRepository<RestaurantRating, Integer> {
    Optional<List<RestaurantRating>> findRestaurantRatingByRestaurant_Id(Long restaurantId);
}
