package com.safesnack.backend.repository;

import com.safesnack.backend.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthorityRepo extends JpaRepository<Authority, Long> {
}