package com.safesnack.backend.controller;

import com.safesnack.backend.container.RestaurantContainer;
import com.safesnack.backend.model.Restaurant;
import com.safesnack.backend.model.RestaurantRating;
import com.safesnack.backend.repository.IRestaurantRatingRepository;
import com.safesnack.backend.repository.IRestaurantRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class RestaurantController {

    IRestaurantRepo restaurantRepo;
    IRestaurantRatingRepository restaurantRatingRepository;

    @GetMapping("/searchRestaurant")
    public ResponseEntity<Page<Restaurant>> searchRestaurant(@RequestParam String keyword,
                                                             @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        try {
            Pageable paging = PageRequest.of(page - 1, size);
            Page<Restaurant> pageRestaurants;
            pageRestaurants = restaurantRepo.findRestaurantByNameContainsIgnoreCase(keyword, paging);
            return ResponseEntity.ok(pageRestaurants);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<RestaurantContainer> getRestaurantById(@PathVariable Long id) {
        try {
            Restaurant restaurant = restaurantRepo.findById(id).orElse(null);
            List<RestaurantRating> restaurantRatings = restaurantRatingRepository.findRestaurantRatingByRestaurant_Id(id).orElse(null);
            RestaurantContainer restaurantContainer = new RestaurantContainer(restaurant, restaurantRatings);
            return ResponseEntity.ok(restaurantContainer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
