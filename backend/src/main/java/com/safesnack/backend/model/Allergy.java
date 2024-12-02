package com.safesnack.backend.model;

import com.safesnack.backend.model.absctractModel.AbstractNamedIdEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "allergy")
public class Allergy extends AbstractNamedIdEntity {
}
