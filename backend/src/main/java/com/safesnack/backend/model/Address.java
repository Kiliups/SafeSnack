package com.safesnack.backend.model;

import com.safesnack.backend.model.absctractModel.AbstractIdEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "address")
public class Address extends AbstractIdEntity {
    private String street;
    private String city;
    private String zip;
}
