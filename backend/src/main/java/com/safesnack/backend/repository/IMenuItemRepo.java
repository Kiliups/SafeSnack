package com.safesnack.backend.repository;

import com.safesnack.backend.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMenuItemRepo extends JpaRepository<MenuItem, Long> {
}
