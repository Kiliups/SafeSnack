package com.safesnack.backend.service;

import com.safesnack.backend.model.RestaurantRating;
import com.safesnack.backend.repository.IRestaurantRatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantRatingService {

    private IRestaurantRatingRepository restaurantRatingRepository;

    public List<RestaurantRating> findAll() {
        return restaurantRatingRepository.findAll();
    }


}
