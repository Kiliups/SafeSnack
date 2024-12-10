package com.safesnack.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.safesnack.backend.model.absctractModel.AbstractNamedIdEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "allergen")
public class Allergen extends AbstractNamedIdEntity {

    @ManyToOne
    @JoinColumn(name = "allergy_id", nullable = true)
    @JsonBackReference // Prevent infinite recursion
    private Allergy allergy;
}