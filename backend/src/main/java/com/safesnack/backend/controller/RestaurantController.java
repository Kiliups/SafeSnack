package com.safesnack.backend.controller;

import com.safesnack.backend.model.Restaurant;
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

@RestController
@AllArgsConstructor
public class RestaurantController {

    IRestaurantRepo restaurantRepo;

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
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        try {
            Restaurant restaurant = restaurantRepo.findById(id).orElse(null);
            return ResponseEntity.ok(restaurant);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
