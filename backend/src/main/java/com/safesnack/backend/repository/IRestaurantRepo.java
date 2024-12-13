package com.safesnack.backend.repository;

import com.safesnack.backend.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRestaurantRepo extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByEmail(String email);
}
