package com.safesnack.backend.controller;

import com.safesnack.backend.model.RestaurantRating;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantRatingController {
    @PostMapping
    public void createRestaurantRating(@RequestBody RestaurantRating restaurantRating) {

    }
}
