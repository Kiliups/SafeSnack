package com.safesnack.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "restaurant")
public class Restaurant extends UserMetaBase {
    @OneToOne
    private Address address;
    private String description;
    @ManyToOne
    private Menu menu;
}