package com.main.airbnb.payload;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
public class ReviewDto {

    private String id;

    @Min(value = 0, message = "Rating must be at least 0")
    @Max(value = 5, message = "Rating must be at most 5")
    private Double rating;

    private String description;

    private String propertyName;

    private String userName;


}
