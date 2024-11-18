package com.safesnack.backend;

import com.safesnack.backend.model.*;
import com.safesnack.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    @Autowired
    private IAuthorityRepo authorityRepo;

    @Autowired
    private IUserPrincipalRepo userPrincipalRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Authority userAuth = Authority.builder()
                .authority(AuthorityEnum.ROLE_USER)
                .build();
        Authority adminAuth = Authority.builder()
                .authority(AuthorityEnum.ROLE_ADMIN)
                .build();

        if (authorityRepo.findAll().isEmpty()) {
            authorityRepo.saveAll(Arrays.asList(
                    userAuth,adminAuth));
        }

        UserMeta admin = UserMeta.builder()
                .name("admin")
                .email("admin@local.de")
                .build();
        UserMeta basicUser = UserMeta.builder()
                .name("basic user")
                .email("user@local.de")
                .build();

        if (userPrincipalRepo.findAll().isEmpty()) {
            userPrincipalRepo.saveAll(
                    Arrays.asList(
                            new UserPrincipal(
                                    "USER",
                                    passwordEncoder.encode("user"),
                                    Collections.singletonList(userAuth),
                                    basicUser),
                            new UserPrincipal(
                                    "ADMIN",
                                    passwordEncoder.encode("admin"),
                                    Arrays.asList(adminAuth, userAuth),
                                    admin)
                    )
            );
        }
    }
}
