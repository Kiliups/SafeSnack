package com.safesnack.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "restaurant")
public class Restaurant extends UserMetaBase {
    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;
    private String description;
    @ManyToOne
    private Menu menu;
}
