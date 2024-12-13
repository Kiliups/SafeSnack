package com.safesnack.backend.container;

import com.safesnack.backend.model.Restaurant;
import com.safesnack.backend.model.RestaurantRating;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RestaurantContainer {
    Restaurant restaurant;
    List<RestaurantRating> restaurantRatings;
}
