package com.main.airbnb.util;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.main.airbnb.entity.Booking;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PdfService {

    public void generatePdf(Booking booking, String filePath) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Booking Confirmation"));
        document.add(new Paragraph("Booking ID: " + booking.getId()));
        document.add(new Paragraph("Customer Name: " + booking.getName()));
        document.add(new Paragraph("Booking Date: " + booking.getDateTime()));
        document.add(new Paragraph("Email : " + booking.getEmail()));
        document.add(new Paragraph("Mobile : " + booking.getMobile()));
        document.add(new Paragraph("GST Amount : " + booking.getGst()));
        BigDecimal totalPrice = BigDecimal.valueOf(booking.getTotalPrice());
        totalPrice = totalPrice.setScale(2, RoundingMode.HALF_UP);
        document.add(new Paragraph("Total Amount : " + totalPrice.doubleValue()));
        document.add(new Paragraph("Hotel Name : " + booking.getProperty().getPropertyName()));
        document.add(new Paragraph("No Of Nights : " + booking.getNoOfNights()));
        document.close();
    }

    public MultipartFile convertFileToMultipartFile(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                file.getName(),
                "application/pdf",
                input
        );
        input.close();
        return multipartFile;

    }
}
