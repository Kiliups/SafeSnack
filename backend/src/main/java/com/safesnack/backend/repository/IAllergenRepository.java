package com.safesnack.backend.repository;

import com.safesnack.backend.model.Allergen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAllergenRepository extends JpaRepository<Allergen, Integer> {
}
