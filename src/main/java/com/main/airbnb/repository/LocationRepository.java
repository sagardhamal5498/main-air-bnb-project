package com.main.airbnb.repository;

import com.main.airbnb.entity.Country;
import com.main.airbnb.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, String> {
    Optional<Location> findByName(String name);
}