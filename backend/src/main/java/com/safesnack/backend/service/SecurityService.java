package com.safesnack.backend.service;

import com.safesnack.backend.exceptions.TokenExpiredException;
import com.safesnack.backend.exceptions.TokenNotFoundException;
import com.safesnack.backend.model.PasswordResetToken;
import com.safesnack.backend.repository.IPasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class SecurityService {

    private final IPasswordResetTokenRepository passwordTokenRepository;

    @Autowired
    public SecurityService(IPasswordResetTokenRepository passwordTokenRepository) {
        this.passwordTokenRepository = passwordTokenRepository;
    }

    public String validatePasswordResetToken(String token) {
        Optional<PasswordResetToken> passToken = passwordTokenRepository.findByToken(token);
        // check if the token is found

        boolean isTokenFound = passToken.isPresent();
        if (!isTokenFound) {
            throw new TokenNotFoundException("invalid Token");
        }

        if (isTokenExpired(passToken.get())) {
            throw new TokenExpiredException("token has expired");
        }
        return passToken.get().getToken();
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        Date expires = passToken.getExpiryDate();
        // check if the token has expired
        return expires.getTime() - System.currentTimeMillis() <= 0;
    }
}
