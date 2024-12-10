package com.safesnack.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.safesnack.backend.model.absctractModel.AbstractIdEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "diary_entry")
public class DiaryEntry extends AbstractIdEntity {

    @Column(insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserPrincipal user;

    @ManyToOne
    @JoinColumn(name = "allergy_id", nullable = true)
    @JsonBackReference // Prevent infinite recursion
    private Allergy allergy;
}
