package com.safesnack.backend.service;

import com.safesnack.backend.exceptions.UserNotFoundException;
import com.safesnack.backend.model.PasswordResetToken;
import com.safesnack.backend.model.UserMeta;
import com.safesnack.backend.model.UserPrincipal;
import com.safesnack.backend.repository.IPasswordResetTokenRepository;
import com.safesnack.backend.repository.IUserMetaRepo;
import com.safesnack.backend.repository.IUserPrincipalRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@NoArgsConstructor
public class UserDataChangeService {

    private IUserPrincipalRepo userPrincipalRepo;
    private IUserMetaRepo userMetaRepo;
    private IPasswordResetTokenRepository passwordResetTokenRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserDataChangeService(IUserPrincipalRepo userPrincipalRepo, IUserMetaRepo userMetaRepo, IPasswordResetTokenRepository passwordResetTokenRepository, PasswordEncoder passwordEncoder) {
        this.userPrincipalRepo = userPrincipalRepo;
        this.userMetaRepo = userMetaRepo;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /// Generates a password reset token for a user.
    /// The token is saved in the database.
    /// The token is generated using a UUID.
    /// The token is linked to the user's UserPrincipal object.
    ///
    /// @param email the email address of the user
    /// @return the generated PasswordResetToken object
    /// @throws UserNotFoundException if the user is not found
    public PasswordResetToken generateResetToken(String email) throws UserNotFoundException {
        UserMeta user = userMetaRepo.findUserMetaByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with email '%s' not found", email)));

        UserPrincipal userPrincipal = userPrincipalRepo.findByuserMeta(user)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("No user principle is linked with user metadata id '%d' " +
                                "(email: '%s', name: '%s')", user.getId(), user.getEmail(), user.getName())));

        String token = UUID.randomUUID().toString();
        PasswordResetToken myToken = new PasswordResetToken(token, userPrincipal);
        passwordResetTokenRepository.save(myToken);
        return myToken;
    }

    /// Changes the password of a user.
    ///
    /// @param userPrincipal the user principal object of the user
    /// @param newPassword   the new password
    public void changeUserPassword(UserPrincipal userPrincipal, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        // update the password
        userPrincipal.setPassword(encodedPassword);

        // save the updated UserPrincipal object to the database
        userPrincipalRepo.save(userPrincipal);
    }
}