package com.safesnack.backend.service;

import com.safesnack.backend.model.RestaurantRating;
import com.safesnack.backend.repository.IRestaurantRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantRatingService {

    private IRestaurantRatingRepository restaurantRatingRepository;

    public Iterable<RestaurantRating> findAll() {
        return restaurantRatingRepository.findAll();
    }


}
