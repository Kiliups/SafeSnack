package com.safesnack.backend.controller;

import com.safesnack.backend.container.PasswordResetContainer;
import com.safesnack.backend.container.UserContainer;
import com.safesnack.backend.exceptions.TokenExpiredException;
import com.safesnack.backend.exceptions.TokenNotFoundException;
import com.safesnack.backend.model.*;
import com.safesnack.backend.repository.*;
import com.safesnack.backend.service.CustomUserService;
import com.safesnack.backend.service.MailService;
import com.safesnack.backend.service.SecurityService;
import com.safesnack.backend.service.UserDataChangeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class UserController {
    private final String FRONTEND_URL = "http://localhost:5173";

    final CustomUserService userDetailsService;
    final IUserPrincipalRepo userPrincipalRepo;
    final IUserMetaRepo userMetaRepo;
    final UserDataChangeService userDataChangeService;
    final IPasswordResetTokenRepository passwordResetTokenRepository;
    private final MailService mailService;
    private final SecurityService securityService;
    private PasswordEncoder passwordEncoder;
    final IRestaurantRepo restaurantRepo;
    final IAuthorityRepo authorityRepo;
    final IAdressRepo adressRepo;

    @PostMapping("/update-user")
    public UserMeta updateUser(@RequestBody UserMeta userToUpdate) {
        return userDetailsService.updateUserMeta(userToUpdate);
    }

    @GetMapping("/current-user")
    // tell Spring to inject the user's Authentication token
    public UserContainer getUser(Authentication authentication) {
        // use the token to retrieve the UserMeta object
        UserPrincipal principal =
                (UserPrincipal) authentication.getPrincipal();
        UserContainer userContainer = new UserContainer();
        userContainer.setUser((UserMeta) principal.getUserMeta());
        userContainer.setRoles(principal.getAuthorities());
        return userContainer;
    }

    @PostMapping("/user/resetPassword")
    public ResponseEntity<String> resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
        try {
            PasswordResetToken token = userDataChangeService.generateResetToken(userEmail);
            String requestUrl = request.getRequestURL().toString();
            // extract base url
            requestUrl = requestUrl.replace(request.getServletPath(), "");
            SimpleMailMessage msg = mailService.constructResetTokenEmail(requestUrl, token.getToken(), userEmail);
            mailService.sendEmail(msg);
        } catch (MailException mailException) {
            System.out.println("Sending Email failed!" + mailException.getMessage());
        } catch (Exception e) {
            System.out.println("Token generation failed!" + e.getMessage());
        }

        return ResponseEntity.ok("If an account with the provided email exists, you will receive a password reset email.");
    }

    @GetMapping("/user/changePassword")
    public void showChangePasswordPage(Model model, @RequestParam("token") String token, HttpServletResponse response) throws IOException {
        try {
            securityService.validatePasswordResetToken(token);
            // will throw if token is invalid or expired. handle in catch block.
            model.addAttribute("token", token);
            response.sendRedirect(FRONTEND_URL + "/auth/forgot/updatePassword?token=" + token);
        } catch (TokenExpiredException | TokenNotFoundException tokenException) {
            System.out.println(tokenException.getMessage());
            response.sendRedirect(FRONTEND_URL + "/auth/login");
        }
    }

    @PostMapping("/user/savePassword")
    public ResponseEntity<String> savePassword(@RequestBody PasswordResetContainer resetContainer) {
        String token = resetContainer.getToken();
        String result = securityService.validatePasswordResetToken(token);

        if (result == null) {
            // wrong token response
            return ResponseEntity.ok("token expired");
        }

        Optional<PasswordResetToken> passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken.isEmpty()) {
            // No token found response
            return ResponseEntity.ok("invalid token");
        }

        // change the password
        userDataChangeService.changeUserPassword(passwordResetToken.get().getUser(), resetContainer.getNewPassword());
        passwordResetTokenRepository.delete(passwordResetToken.get());
        return ResponseEntity.ok("successfully changed password");
    }

    @PostMapping("/signup/{password}")
    public ResponseEntity<String> signUp(@RequestBody UserPrincipal user, @PathVariable String password) {
        if (userPrincipalRepo.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists. Log in instead.");
        }
        UserMetaBase userMeta = user.getUserMeta();
        if (userMeta instanceof UserMeta) {
            if (userMetaRepo.findUserMetaByEmail(userMeta.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email already in use. Log in instead.");
            }
            saveUserPrincipal(user, password);
            userMetaRepo.save((UserMeta) userMeta);
            return ResponseEntity.ok("User account created successfully.");
        } else if (userMeta instanceof Restaurant) {
            if (restaurantRepo.findByEmail(userMeta.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email already in use. Log in instead.");
            }
            final Address address = ((Restaurant) userMeta).getAddress();
            if (address != null) {
                ((Restaurant) userMeta).setAddress(adressRepo.save(address));
            }
            saveUserPrincipal(user, password);
            restaurantRepo.save((Restaurant) userMeta);
            return ResponseEntity.ok("Restaurant account created successfully.");
        } else {
            return ResponseEntity.badRequest().body("UserMeta type not recognized.");
        }
    }

    private void saveUserPrincipal(UserPrincipal user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        user.setUsername(user.getUsername().toUpperCase());// Uppercase since usernames are in capslock in the db
        authorityRepo.findAll().forEach((role) -> {
            if (Objects.equals(role.getAuthority(), AuthorityEnum.ROLE_USER.toString())) {
                user.setAuthorities(Collections.singletonList(role));
            }
        });
        userPrincipalRepo.save(user);
    }
}