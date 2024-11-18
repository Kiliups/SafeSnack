package com.safesnack.backend.service;

import com.safesnack.backend.model.*;
import com.safesnack.backend.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.*;

@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    IUserPrincipalRepo userPrincipalRepo;

    @Autowired
    IUserMetaRepo userMetaRepo;

    @Override
    public UserPrincipal loadUserByUsername(String username)
            throws UsernameNotFoundException {

        return userPrincipalRepo
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with username or email : "
                                + username)
                );
    }

    public UserMeta updateUserMeta(UserMeta userToUpdate) {
        // save the user to the database
        UserMeta updatedUser = userMetaRepo.save(userToUpdate);

        // after the user has been saved to the database the UserPrincipal
        // object stored in the security context has become stale and
        // also needs to be updated. especially since we are using the
        // SecurityContext to return user data in the UserController.
        UserPrincipal userPrincipal = (UserPrincipal)
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();
        userPrincipal.setUserMeta(userToUpdate);
        return updatedUser;
    }
}

