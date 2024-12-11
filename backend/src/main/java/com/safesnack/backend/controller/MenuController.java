package com.safesnack.backend.controller;

import com.safesnack.backend.model.Menu;
import com.safesnack.backend.model.Restaurant;
import com.safesnack.backend.repository.IMenuRepo;
import com.safesnack.backend.repository.IRestaurantRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.safesnack.backend.repository.IMenuRepo;

@RestController
@AllArgsConstructor
public class MenuController {

    private final IMenuRepo menuRepo;

    @GetMapping("/menus")
    public ResponseEntity<List<Menu>> getMenus() {
        return ResponseEntity.ok(menuRepo.findAll());
    }

    @GetMapping("/menu")
    public ResponseEntity<Menu> getMenu(@RequestParam long id) {
        Optional<Menu> menu = menuRepo.findById(id);
        return menu.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().body(null));
    }

    // @GetMapping("/menus-by-restaurant")
    // public ResponseEntity<List<Menu>> findAllByRestaurant(@RequestParam long restaurantId) {
    //    return ResponseEntity.ok(menuRepo.findAllByRestaurant(restaurantId));
    //}
}