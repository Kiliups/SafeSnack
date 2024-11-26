package com.safesnack.backend.controller;

import com.safesnack.backend.container.PasswordResetContainer;
import com.safesnack.backend.container.UserContainer;
import com.safesnack.backend.model.PasswordResetToken;
import com.safesnack.backend.model.UserMeta;
import com.safesnack.backend.model.UserPrincipal;
import com.safesnack.backend.repository.IPasswordResetTokenRepository;
import com.safesnack.backend.repository.IUserMetaRepo;
import com.safesnack.backend.repository.IUserPrincipalRepo;
import com.safesnack.backend.service.CustomUserService;
import com.safesnack.backend.service.MailService;
import com.safesnack.backend.service.SecurityService;
import com.safesnack.backend.service.UserDataChangeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    final CustomUserService userDetailsService;
    final IUserPrincipalRepo userPrincipalRepo;
    final IUserMetaRepo userMetaRepo;
    final UserDataChangeService userDataChangeService;
    final IPasswordResetTokenRepository passwordResetTokenRepository;

    private final MailService mailService;
    private final SecurityService securityService;

    public UserController(CustomUserService userDetailsService, IUserPrincipalRepo userPrincipalRepo, IUserMetaRepo userMetaRepo, UserDataChangeService userDataChangeService, IPasswordResetTokenRepository passwordResetTokenRepository, MailService mailService, SecurityService securityService) {
        this.userDetailsService = userDetailsService;
        this.userPrincipalRepo = userPrincipalRepo;
        this.userMetaRepo = userMetaRepo;
        this.userDataChangeService = userDataChangeService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.mailService = mailService;
        this.securityService = securityService;
    }

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
        userContainer.setUser(principal.getUserMeta());
        userContainer.setRoles(principal.getAuthorities());
        return userContainer;
    }

    @PostMapping("/user/resetPassword")
    public ResponseEntity<String> resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
        try {
            PasswordResetToken token = userDataChangeService.generateResetToken(userEmail);
            String requestUrl = request.getRequestURL().toString();
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
    public String showChangePasswordPage(Model model, @RequestParam("token") String token) {
        String result = securityService.validatePasswordResetToken(token);
        if (result != null) {
            // TODO: Implement localization
            String message = "invalid token";
            // return redirect
            return "redirect:/login" + "&message=" + message;
        } else {
            model.addAttribute("token", token);
            return "redirect:/login/updatePassword";
        }
    }

    @PostMapping("/user/savePassword")
    public ResponseEntity<String> savePassword(@RequestBody PasswordResetContainer resetContainer) {
        String token = resetContainer.getToken();
        String result = securityService.validatePasswordResetToken(token);

        if (result != null) {
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
        return ResponseEntity.ok("successfully changed password");
    }
}