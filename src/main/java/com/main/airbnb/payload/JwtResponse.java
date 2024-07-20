package com.main.airbnb.payload;

import lombok.Data;

@Data
public class JwtResponse {

    private String type;
    private String token;
}
