package com.safesnack.backend.repository;

import com.safesnack.backend.model.Menu;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.function.Function;

public interface IMenuRepo extends JpaRepository<Menu, Long> {
    //List<Menu> findAllByRestaurant(Long restaurantId);
}
