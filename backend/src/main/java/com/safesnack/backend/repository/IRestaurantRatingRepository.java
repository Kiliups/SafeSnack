package com.safesnack.backend.repository;

import com.safesnack.backend.model.RestaurantRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRestaurantRatingRepository extends JpaRepository<RestaurantRating, Integer> {

}
