package com.safesnack.backend.repository;

import com.safesnack.backend.model.UserMeta;
import com.safesnack.backend.model.UserPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserPrincipalRepo extends JpaRepository<UserPrincipal, Long> {

    Optional<UserPrincipal> findByUsername(String username);

    Optional<UserPrincipal> findByuserMeta(UserMeta userMeta);
}