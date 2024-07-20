package com.main.airbnb.service;

import com.main.airbnb.payload.PropertyDto;
import com.main.airbnb.payload.PropertyReturnDto;

import java.util.List;

public interface PropertyService {
    PropertyReturnDto addProperty(PropertyDto dto);

    PropertyReturnDto updateProperty(PropertyDto dto, String propertyId);

    List<PropertyReturnDto> searchHotel(String locationName);

    String deleteProperty(String propertyId);


}
