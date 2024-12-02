package com.safesnack.backend.repository;

import com.safesnack.backend.model.UserMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserMetaRepo extends JpaRepository<UserMeta, Long> {

    Optional<UserMeta> findUserMetaByEmail(String email);

    Optional<UserMeta> findUserMetaByName(String name);
}