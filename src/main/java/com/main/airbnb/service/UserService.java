package com.main.airbnb.service;

import com.main.airbnb.payload.UserDto;
import com.main.airbnb.payload.loginDto;

public interface UserService {
    UserDto signUpUser(UserDto dto);

    String loginUser(loginDto dto);
}
