package com.safesnack.backend.controller;

import com.safesnack.backend.model.RestaurantRating;
import com.safesnack.backend.repository.IRestaurantRatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class RestaurantRatingController {

    private final IRestaurantRatingRepository restaurantRatingRepository;

    @PostMapping("/restaurantRating")
    public ResponseEntity<RestaurantRating> addRestaurantRating(@RequestBody RestaurantRating restaurantRating) {
        try {
            RestaurantRating newRestaurantRating = restaurantRatingRepository.save(restaurantRating);
            return ResponseEntity.ok(newRestaurantRating);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping("/restaurantRating")
    public ResponseEntity<RestaurantRating> updateRestaurantRating(@RequestBody RestaurantRating restaurantRating) {
        try {
            RestaurantRating newRestaurantRating = restaurantRatingRepository.save(restaurantRating);
            return ResponseEntity.ok(newRestaurantRating);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("/restaurantRating/{id}")
    public ResponseEntity<String> deleteRestaurantRating(@PathVariable Integer id) {
        try {
            restaurantRatingRepository.deleteById(id);
            return ResponseEntity.ok("Restaurant rating deleted successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
