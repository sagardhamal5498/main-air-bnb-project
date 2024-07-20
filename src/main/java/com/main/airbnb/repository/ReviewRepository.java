package com.main.airbnb.repository;

import com.main.airbnb.entity.Property;
import com.main.airbnb.entity.Review;
import com.main.airbnb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, String> {

    @Query("select r from Review r where r.property=:property and r.user=:user")
    Optional<Review> findByPropertyAndUser(@Param("property") Property property, @Param("user") User user);

    @Query("select r from Review r where r.user=:user")
    List<Review> findByUser(@Param("user") User user);
}