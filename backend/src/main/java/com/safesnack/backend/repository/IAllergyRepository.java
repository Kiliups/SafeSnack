package com.safesnack.backend.repository;

import com.safesnack.backend.model.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAllergyRepository extends JpaRepository<Allergy, Integer> {

}