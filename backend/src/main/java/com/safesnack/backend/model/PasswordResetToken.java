package com.safesnack.backend.model;

import com.safesnack.backend.model.absctractModel.AbstractIdEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetToken extends AbstractIdEntity {

    private static final int EXPIRATION_MIN = 10;

    public PasswordResetToken(String token, UserPrincipal user) {
        this.token = token;
        this.user = user;
        this.expiryDate = new Date(System.currentTimeMillis() + EXPIRATION_MIN * 60 * 1000);
    }

    private String token;

    @OneToOne(targetEntity = UserPrincipal.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private UserPrincipal user;

    private Date expiryDate;
}