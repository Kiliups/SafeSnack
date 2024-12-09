package com.safesnack.backend.controller;

import com.safesnack.backend.model.*;
import com.safesnack.backend.repository.IAllergenRepository;
import com.safesnack.backend.repository.IAllergyRepository;
import com.safesnack.backend.service.CustomUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AllergyController {

    private final CustomUserService userService;
    private final IAllergyRepository allergyRepo;
    private final IAllergenRepository allergenRepo;

    public AllergyController(CustomUserService userService, IAllergyRepository allergyRepo, IAllergenRepository allergenRepo) {
        this.userService = userService;
        this.allergyRepo = allergyRepo;
        this.allergenRepo = allergenRepo;
    }

    @GetMapping("/allAllergies")
    public ResponseEntity<List<Allergy>> getAllAllergies() {
        try {
            var allergies = allergyRepo.findAll();
            return ResponseEntity.ok(allergies);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/allergy")
    public ResponseEntity<List<Allergy>> getAllergy(Authentication authentication) {
        UserPrincipal principal =
                (UserPrincipal) authentication.getPrincipal();

        if (principal.getUserMeta() instanceof UserMeta meta) {
            return ResponseEntity.ok(meta.getAllergies());
        } else {
            // restaurant has called this endpoint -> not allowed
            return ResponseEntity.status(405).body(null);
        }
    }

    @PostMapping("/allergy")
    public ResponseEntity<String> assignAllergy(Authentication authentication, @RequestBody Allergy allergy) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        if (principal.getUserMeta() instanceof UserMeta meta) {
            // check if allergy is in repo first
            Optional<Allergy> dbAllergy = allergyRepo.findByName(allergy.getName());
            if (dbAllergy.isEmpty())
                return ResponseEntity.status(400).body("Allergy not found");

            // add allergy to user
            meta.getAllergies().add(dbAllergy.get());
            userService.updateUserMeta(meta);
            return ResponseEntity.ok("Allergy added");
        } else {
            // restaurant has called this endpoint -> not allowed
            return ResponseEntity.status(405).body("Not allowed for Restaurant");
        }
    }

    @DeleteMapping("/allergy")
    public ResponseEntity<String> removeAllergy(Authentication authentication, @RequestBody Allergy allergy) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        if (principal.getUserMeta() instanceof UserMeta meta) {
            meta.getAllergies().remove(allergy);
            userService.updateUserMeta(meta);
            return ResponseEntity.ok("Allergy removed");
        } else {
            // restaurant has called this endpoint -> not allowed
            return ResponseEntity.status(405).body("Not allowed for Restaurant");
        }
    }

    @PutMapping("/allergy")
    public void updateAllergyList(Authentication authentication) {
        // update allergy?
    }

    // Admin endpoints

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create-allergy")
    public ResponseEntity<Allergy> createAllergy(Authentication authentication, @RequestBody Allergy allergy) {

        // get role of user
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        List<Authority> role = principal.getAuthorities();

        try {
            allergyRepo.save(allergy);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
        System.out.println("new Allergy added to Db: " + allergy.getName());
        return ResponseEntity.ok(allergy);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete-allergy")
    public ResponseEntity<String> deleteAllergy(@RequestBody Allergy allergy) {
        try {
            allergyRepo.delete(allergy);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        System.out.println("deleted Allergy from Db: " + allergy.getName());
        return ResponseEntity.ok("Allergy deleted");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/update-allergy")
    public ResponseEntity<String> updateAllergy(@RequestBody Allergy allergy) {
        try {
            allergyRepo.save(allergy);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        System.out.println("updated Allergy in Db: " + allergy.getName());
        return ResponseEntity.ok("Allergy updated");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/assign-allergen")
    public ResponseEntity<String> assignAllergen(@RequestParam long allergyId, @RequestParam long allergenId) {
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
    @PostMapping("/assign-allergens")
    public ResponseEntity<String> assignAllergens(@RequestParam long allergyId, @RequestBody List<Allergen> allergens) {
        Optional<Allergy> allergy = allergyRepo.findById(allergyId);

        if (allergy.isEmpty()) {
            return ResponseEntity.badRequest().body("Allergy not found");
        }

        Allergy allergyValue = allergy.get();

        for (Allergen allergen : allergens) {
            allergen.setAllergy(allergyValue);
        }
        allergyValue.setAllergens(allergens);

        allergyRepo.save(allergyValue);
        allergenRepo.saveAll(allergens);

        return ResponseEntity.ok("Allergens assigned to Allergy '" + allergyValue.getName() + "'");
    }
}
