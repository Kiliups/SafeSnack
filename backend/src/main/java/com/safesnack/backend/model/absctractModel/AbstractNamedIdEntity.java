package com.safesnack.backend.model.absctractModel;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
public class AbstractNamedIdEntity extends AbstractIdEntity {
    @Column(name = "name", nullable = false)
    private String name;
}
