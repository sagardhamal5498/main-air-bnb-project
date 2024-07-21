package com.main.airbnb.service;

import com.main.airbnb.entity.Booking;
import com.main.airbnb.entity.Property;
import com.main.airbnb.entity.User;
import com.main.airbnb.exception.PropertyNotFoundException;
import com.main.airbnb.payload.BookingDto;
import com.main.airbnb.repository.BookingRepository;
import com.main.airbnb.repository.PropertyRepository;
import com.main.airbnb.util.EmailService;
import com.main.airbnb.util.PdfService;
import com.main.airbnb.util.SmsService;
import com.main.airbnb.util.WhatsAppService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService{

    private BookingRepository bookingRepository;
    private PropertyRepository propertyRepository;
    private EmailService emailService;
    private SmsService smsService;
    private WhatsAppService whatsAppService;
    private PdfService pdfService;
    private S3Service s3Service;

    public BookingServiceImpl(BookingRepository bookingRepository, PropertyRepository propertyRepository, EmailService emailService, SmsService smsService, WhatsAppService whatsAppService, PdfService pdfService, S3Service s3Service) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.emailService = emailService;
        this.smsService = smsService;
        this.whatsAppService = whatsAppService;
        this.pdfService = pdfService;
        this.s3Service = s3Service;
    }

    @Override
    public BookingDto createBooking(BookingDto dto, String propertyId, User user) throws IOException {
         Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new PropertyNotFoundException("Property Not Found")
        );
         Booking booking = bookingDtoToEntity(dto, property, user);
         Booking savedBooking = bookingRepository.save(booking);
         BookingDto bookingDto = bookingEntityToDto(savedBooking);
         pdfService.generatePdf(savedBooking,"D:\\Actual Main Project\\main-airbnb\\tickets\\"+savedBooking.getId()+"-confirmation.pdf");
         MultipartFile multipartFile = pdfService.convertFileToMultipartFile("D:\\Actual Main Project\\main-airbnb\\tickets\\" + savedBooking.getId() + "-confirmation.pdf");
         String pdfUrl = s3Service.uploadFile(multipartFile);
        emailService.sendSimpleEmail(savedBooking.getEmail(),"Booking Confirmation Mail","Your booking is confirmed click here....." + pdfUrl);
         smsService.sendSms(savedBooking.getMobile(),"Your booking is confirmed click here....." + pdfUrl);
         whatsAppService.sendWhatsappMessage(savedBooking.getMobile(),"Your booking is confirmed click here....." + pdfUrl);
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
        booking.setLocalDateTime(savedBooking.getDateTime());
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
         booking.setDateTime(LocalDateTime.now());
         return booking;


    }
}
