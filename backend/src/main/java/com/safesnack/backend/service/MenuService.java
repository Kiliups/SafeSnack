package com.safesnack.backend.service;

import com.safesnack.backend.model.Menu;
import com.safesnack.backend.model.Restaurant;
import com.safesnack.backend.model.RestaurantRating;
import com.safesnack.backend.repository.IMenuRepo;
import com.safesnack.backend.repository.IRestaurantRatingRepository;
import com.safesnack.backend.repository.IRestaurantRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MenuService {

    private IMenuRepo menuRepo;
    private IRestaurantRepo restaurantRepo;

    public List<Menu> findAll() {
        return menuRepo.findAll();
    }
    public Optional<Menu> findById(long id) { return menuRepo.findById(id);}
    //public List<Menu> findAllByRestaurant(long restaurantId) {
    //    return menuRepo.findAllByRestaurant(restaurantId);
    //}


}
