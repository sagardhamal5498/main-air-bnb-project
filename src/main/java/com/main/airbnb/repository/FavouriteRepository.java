package com.main.airbnb.repository;

import com.main.airbnb.entity.Favourite;
import com.main.airbnb.entity.Property;
import com.main.airbnb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavouriteRepository extends JpaRepository<Favourite, String> {
    Optional<Favourite> findByUserAndProperty(User user, Property property);
}