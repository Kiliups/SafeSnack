package com.safesnack.backend.model.absctractModel;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public class AbstractIdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}