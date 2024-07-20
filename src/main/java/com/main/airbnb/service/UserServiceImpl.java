package com.main.airbnb.service;

import com.main.airbnb.entity.User;
import com.main.airbnb.exception.UserAlreadyExistsException;
import com.main.airbnb.payload.UserDto;
import com.main.airbnb.payload.loginDto;
import com.main.airbnb.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public UserDto signUpUser(UserDto dto) {
         Optional<User>  opUser = userRepository.findByUsername(dto.getUsername());
         if(opUser.isPresent()){
             throw new UserAlreadyExistsException("user is already exists");
         }
        User user = userDtoToEntity(dto);
         User savedUser = userRepository.save(user);
         UserDto userDto = userEntityToDto(savedUser);
        return userDto;
    }

    @Override
    public String loginUser(loginDto dto) {
      User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(
                 ()-> new  UsernameNotFoundException("user not found")
         );
      if(BCrypt.checkpw(dto.getPassword(),user.getPassword())){
             String token = jwtService.generateToken(user);
             return token;
      }
      return null;

    }
    private UserDto userEntityToDto(User savedUser) {
        UserDto user = new UserDto();
        user.setUserId(savedUser.getUserId());
        user.setName(savedUser.getName());
        user.setEmail(savedUser.getEmail());
        user.setUsername(savedUser.getUsername());
        user.setPassword(savedUser.getPassword());
        user.setRole(savedUser.getRole());
        return user;
    }

    private User userDtoToEntity(UserDto dto) {
        User user = new User();
         String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(BCrypt.hashpw(dto.getPassword(),BCrypt.gensalt(10)));
        user.setRole(dto.getRole());
        return user;
    }
}
