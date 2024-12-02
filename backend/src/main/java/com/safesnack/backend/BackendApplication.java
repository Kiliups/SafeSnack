package com.safesnack.backend;

import com.safesnack.backend.model.Authority;
import com.safesnack.backend.model.AuthorityEnum;
import com.safesnack.backend.model.UserMeta;
import com.safesnack.backend.model.UserPrincipal;
import com.safesnack.backend.repository.IAuthorityRepo;
import com.safesnack.backend.repository.IUserPrincipalRepo;
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
                    userAuth, adminAuth));
        }

        UserMeta admin = new UserMeta();
        admin.setName("admin");
        admin.setEmail("admin@local.de");

        UserMeta basicUser = new UserMeta();
        basicUser.setName("basic user");
        basicUser.setEmail("user@local.de");

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
