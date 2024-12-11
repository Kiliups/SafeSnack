package com.safesnack.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "restaurant")
public class Restaurant extends UserMetaBase {
    @OneToOne
    private Address address;
    private String description;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference // Prevent infinite recursion
    private List<Menu> menu;
}
