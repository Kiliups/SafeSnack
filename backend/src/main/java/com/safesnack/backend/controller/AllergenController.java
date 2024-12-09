package com.safesnack.backend.controller;

import com.safesnack.backend.model.Allergen;
import com.safesnack.backend.model.Allergy;
import com.safesnack.backend.repository.IAllergenRepository;
import com.safesnack.backend.repository.IAllergyRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AllergenController {

    private final IAllergenRepository allergenRepo;
    private final IAllergyRepository allergyRepo;

    public AllergenController(IAllergenRepository allergenRepo, IAllergyRepository allergyRepo) {
        this.allergenRepo = allergenRepo;
        this.allergyRepo = allergyRepo;
    }

    @GetMapping("/allergens")
    public ResponseEntity<List<Allergen>> getAllergens() {
        return ResponseEntity.ok(allergenRepo.findAll());
    }

    @GetMapping("/allergen")
    public ResponseEntity<Allergen> getAllergen(@RequestParam long id) {
        Optional<Allergen> allergen = allergenRepo.findById(id);
        return allergen.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().body(null));
    }

    @GetMapping("/allergen-by-allergy")
    public ResponseEntity<List<Allergen>> getAllergenByAllergy(@RequestParam long allergyId) {
        Optional<Allergy> allergy = allergyRepo.findById(allergyId);
        return allergy.map(value -> ResponseEntity.ok(value.getAllergens()))
                .orElseGet(() -> ResponseEntity.badRequest().body(null));
    }

    // admin features
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/allergen")
    public ResponseEntity<String> createAllergen(@RequestParam String name) {
        Optional<Allergen> dbAllergen = allergenRepo.findByName(name);
        if (dbAllergen.isPresent()) {
            return ResponseEntity.badRequest().body("Allergen already exists");
        }

        Allergen allergen = new Allergen();
        allergen.setName(name);
        allergenRepo.save(allergen);
        return ResponseEntity.ok("Allergen '" + name + "' created");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/assign-allergen")
    public ResponseEntity<String> assignAllergen(@RequestParam long allergenId, @RequestParam long allergyId) {
        Optional<Allergen> allergen = allergenRepo.findById(allergenId);
        Optional<Allergy> allergy = allergyRepo.findById(allergyId);

        if (allergen.isEmpty() || allergy.isEmpty()) {
            return ResponseEntity.badRequest().body("Allergen or Allergy not found");
        }

        Allergen allergenValue = allergen.get();
        Allergy allergyValue = allergy.get();

        allergyValue.getAllergens().add(allergenValue);
        allergyRepo.save(allergyValue);
        return ResponseEntity.ok("Allergen '" + allergenValue.getName() + "' assigned to Allergy '" + allergyValue.getName() + "'");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/allergen")
    public ResponseEntity<String> updateAllergen(@RequestBody Allergen allergen) {
        try {
            allergenRepo.save(allergen);
            return ResponseEntity.ok("Allergen updated");
        } catch (Exception e) {
            System.out.println("Error updating allergen: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Error updating allergen");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/allergen")
    public ResponseEntity<String> deleteAllergen(@RequestParam long id) {
        try {
            allergenRepo.deleteAllergenById(id);
            return ResponseEntity.ok("Allergen deleted");
        } catch (Exception e) {
            System.out.println("Error deleting allergen: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Error deleting allergen");
        }
    }
}