package com.safesnack.backend.controller;

import com.safesnack.backend.model.RestaurantRating;
import com.safesnack.backend.service.RestaurantRatingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@AllArgsConstructor
public class RestaurantRatingController {

    private final RestaurantRatingService service;

    @GetMapping("/restaurantRating")
    public List<RestaurantRating> getRestaurantRating() {
        Logger logger = Logger.getLogger(RestaurantRatingController.class.getName());
        logger.info("RestaurantRatingController.getRestaurantRating()");
        return service.findAll();
    }
}
