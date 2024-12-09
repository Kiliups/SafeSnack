package com.safesnack.backend.model;

import com.safesnack.backend.model.absctractModel.AbstractNamedIdEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "allergy")
public class Allergy extends AbstractNamedIdEntity {

    @OneToMany(mappedBy = "allergy",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    public List<Allergen> allergens;
}
