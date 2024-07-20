package com.main.airbnb.config;

import com.main.airbnb.entity.User;
import com.main.airbnb.repository.UserRepository;
import com.main.airbnb.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtRequestMapper extends OncePerRequestFilter {

    private JwtService jwtService;
    private UserRepository userRepository;

    public JwtRequestMapper(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         String tokenHeader = request.getHeader("Authorization");
         if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")){
              String token = tokenHeader.substring(8, tokenHeader.length() - 1);
              String username = jwtService.getUsername(token);
              User user = userRepository.findByUsername(username).get();
              String userRole = user.getRole().toString();
             UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                     user,
                     null,
                     Collections.singleton(new SimpleGrantedAuthority(userRole)));
              authToken.setDetails(new WebAuthenticationDetails(request));
             SecurityContextHolder.getContext().setAuthentication(authToken);
         }
         filterChain.doFilter(request,response);
    }
}
