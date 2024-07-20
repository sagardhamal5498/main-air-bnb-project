package com.main.airbnb.payload;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class ImageDto {

    private String id;

    private String imageUrl;

    private String propertyName;

    private String fileName;
}
