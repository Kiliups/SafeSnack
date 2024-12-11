package com.safesnack.backend.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.safesnack.backend.model.absctractModel.AbstractNamedIdEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "menu_item")
public class MenuItem extends AbstractNamedIdEntity {
    private String description;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    @JsonBackReference // Prevent infinite recursion
    private Menu menu;

    // TODO: Add many to many menuItem-Allergen
    // @ManyToMany
    // @JsonManagedReference // Prevent infinite recursion
    // private List<Allergen> allergens;
}
