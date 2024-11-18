package com.safesnack.backend.controller;

import com.safesnack.backend.model.RestaurantRating;
import com.safesnack.backend.service.RestaurantRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/restaurantRating")
@RequiredArgsConstructor
public class RestaurantRatingController {

    private final RestaurantRatingService service;

    @PostMapping
    public void createRestaurantRating(@RequestBody RestaurantRating restaurantRating) {

    }
    @GetMapping
    public List<RestaurantRating> getRestaurantRating() {
        return (List<RestaurantRating>) service.findAll();
    }
}
