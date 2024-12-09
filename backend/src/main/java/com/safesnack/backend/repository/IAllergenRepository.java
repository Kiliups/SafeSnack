package com.safesnack.backend.repository;

import com.safesnack.backend.model.Allergen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAllergenRepository extends JpaRepository<Allergen, Integer> {

    Optional<Allergen> findByName(String name);

    Optional<Allergen> findById(Long id);

    Optional<Allergen> deleteAllergenById(Long id);
}
