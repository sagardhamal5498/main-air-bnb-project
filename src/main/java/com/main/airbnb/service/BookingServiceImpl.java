package com.main.airbnb.service;

import com.main.airbnb.entity.Booking;
import com.main.airbnb.entity.Property;
import com.main.airbnb.entity.User;
import com.main.airbnb.exception.PropertyNotFoundException;
import com.main.airbnb.payload.BookingDto;
import com.main.airbnb.repository.BookingRepository;
import com.main.airbnb.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService{

    private BookingRepository bookingRepository;
    private PropertyRepository propertyRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, PropertyRepository propertyRepository) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    public BookingDto createBooking(BookingDto dto, String propertyId, User user) {
         Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new PropertyNotFoundException("Property Not Found")
        );
         Booking booking = bookingDtoToEntity(dto, property, user);
         Booking savedBooking = bookingRepository.save(booking);
         BookingDto bookingDto = bookingEntityToDto(savedBooking);
         return bookingDto;
    }

    private BookingDto bookingEntityToDto(Booking savedBooking) {
         BookingDto booking = new BookingDto();
        booking.setId(savedBooking.getId());
        booking.setName(savedBooking.getName());
        booking.setEmail(savedBooking.getEmail());
        booking.setPropertyName(savedBooking.getProperty().getPropertyName());
        booking.setGst(savedBooking.getGst());
        DecimalFormat df = new DecimalFormat("#.00");
        booking.setTotalPrice(Double.valueOf(df.format(savedBooking.getTotalPrice())));
        booking.setMobile(savedBooking.getMobile());
        booking.setNoOfNights(savedBooking.getNoOfNights());
        return booking;
    }

    private Booking bookingDtoToEntity(BookingDto dto, Property property, User user) {
         Booking booking = new Booking();
         booking.setId(UUID.randomUUID().toString());
         booking.setName(dto.getName());
         booking.setEmail(dto.getEmail());
         booking.setUser(user);
         Double price = dto.getNoOfNights()*property.getPrice();
         Double gst = price * 0.18;
         booking.setGst(gst);
         booking.setTotalPrice(gst+price);
         booking.setProperty(property);
         booking.setMobile(dto.getMobile());
         booking.setNoOfNights(dto.getNoOfNights());
         return booking;


    }
}
