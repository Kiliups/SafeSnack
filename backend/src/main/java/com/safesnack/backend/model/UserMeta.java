package com.safesnack.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "user_meta")
public class UserMeta extends UserMetaBase {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "userMeta_allergy_join_table",
            joinColumns = @JoinColumn(name = "user_meta_id"),
            inverseJoinColumns = @JoinColumn(name = "allergy_id")
    )
    private List<Allergy> allergies;
}