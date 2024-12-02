package com.safesnack.backend.repository;

import com.safesnack.backend.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdressRepo extends JpaRepository<Address, Long> {
}
