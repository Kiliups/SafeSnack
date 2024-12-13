package com.safesnack.backend.utils;

import com.safesnack.backend.repository.IRestaurantRepo;
import com.safesnack.backend.repository.IUserMetaRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMetaFactory {
    IUserMetaRepo userMetaRepo;
    IRestaurantRepo restaurantRepo;
}
