package com.safesnack.backend.service;

import com.safesnack.backend.model.RestaurantRating;
import com.safesnack.backend.repository.RestaurantRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantRatingService {

    private RestaurantRatingRepository restaurantRatingRepository;

    public Iterable<RestaurantRating> findAll() {
        return restaurantRatingRepository.findAll();
    }


}
