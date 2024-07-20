package com.main.airbnb.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.main.airbnb.entity.Country;
import com.main.airbnb.entity.Location;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PropertyDto {

    private String propertyId;

    @NotBlank(message = "property is mandatory")
    @Size(min = 2,message = "PropertyName must contain at least 2 characters")
    private String propertyName;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "Number of Bedroom cannot be null")
    @Min(value = 1, message = "Number of Bedrooms should not be less than 1")
    private Integer noOfBedrooms;

    private String locationId;

    @NotBlank(message = "description is mandatory")
    @Size(min = 5,message = "PropertyName must contain at least 2 characters")
    private String description;

    private String  countryId;

    @NotNull(message = "Number of guests cannot be null")
    @Min(value = 1, message = "Number of guests should not be less than 1")
    private Integer noOfGuests;

}
