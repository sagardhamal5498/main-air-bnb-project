package com.main.airbnb.exception;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class ExceptionDetails {

    private String webUrl;
    private LocalDateTime dateTime;
    private String message;


}
