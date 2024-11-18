package com.safesnack.backend.controller;

import com.safesnack.backend.model.*;
import com.safesnack.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    CustomUserService userDetailsService;

    @PostMapping("/update-user")
    public UserMeta updateUser(@RequestBody UserMeta userToUpdate) {
        return userDetailsService.updateUserMeta(userToUpdate);
    }

    @GetMapping("/current-user")
    // tell Spring to inject the user's Authentication token
    public UserMeta getUser(Authentication authentication) {
        // use the token to retrieve the UserMeta object
        UserPrincipal principal =
                (UserPrincipal) authentication.getPrincipal();
        return principal.getUserMeta();
    }
}

