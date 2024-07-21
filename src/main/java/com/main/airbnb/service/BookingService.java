package com.main.airbnb.service;

import com.main.airbnb.entity.User;
import com.main.airbnb.payload.BookingDto;

public interface BookingService {
    BookingDto createBooking(BookingDto dto, String propertyId, User user);
}
