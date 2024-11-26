package com.safesnack.backend.controller;

import com.safesnack.backend.container.UserContainer;
import com.safesnack.backend.model.UserMeta;
import com.safesnack.backend.model.UserPrincipal;
import com.safesnack.backend.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public UserContainer getUser(Authentication authentication) {
        // use the token to retrieve the UserMeta object
        UserPrincipal principal =
                (UserPrincipal) authentication.getPrincipal();
        UserContainer userContainer = new UserContainer();
        userContainer.setUser(principal.getUserMeta());
        userContainer.setRoles(principal.getAuthorities());
        return userContainer;
    }
}

