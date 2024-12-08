package com.safesnack.backend.repository;

import com.safesnack.backend.model.PasswordResetToken;
import com.safesnack.backend.model.UserPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);
    
    Optional<PasswordResetToken> findByUser(UserPrincipal user);
}
