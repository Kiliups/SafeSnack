package com.safesnack.backend.model;

import com.safesnack.backend.model.absctractModel.AbstractIdEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "restaurant_rating")
public class RestaurantRating extends AbstractIdEntity {
    @Column(name = "rating", nullable = false)
    private Integer rating;
    @Column(name = "comment")
    private String comment;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserPrincipal user;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
