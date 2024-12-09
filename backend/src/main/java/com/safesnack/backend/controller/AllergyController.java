package com.safesnack.backend.controller;

import com.safesnack.backend.model.Allergy;
import com.safesnack.backend.model.Authority;
import com.safesnack.backend.model.UserMeta;
import com.safesnack.backend.model.UserPrincipal;
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

    public AllergyController(CustomUserService userService, IAllergyRepository allergyRepo) {
        this.userService = userService;
        this.allergyRepo = allergyRepo;
    }

    @GetMapping("/allAllergies")
    public ResponseEntity<List<Allergy>> getAllAllergies() {
        return ResponseEntity.ok(allergyRepo.findAll());
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
    public ResponseEntity<String> createAllergy(Authentication authentication, @RequestBody Allergy allergy) {

        // get role of user
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        List<Authority> role = principal.getAuthorities();

        try {
            allergyRepo.save(allergy);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        System.out.println("new Allergy added to Db: " + allergy.getName());
        return ResponseEntity.ok("Allergy '" + allergy.getName() + "' added");
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
}
