package com.safesnack.backend.repository;

import com.safesnack.backend.model.UserMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserMetaRepo extends JpaRepository<UserMeta, Long> {
}