package com.main.airbnb.repository;

import com.main.airbnb.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, String> {
   Optional<Country> findByName(String name);
}