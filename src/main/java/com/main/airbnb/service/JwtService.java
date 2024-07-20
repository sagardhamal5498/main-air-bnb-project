package com.main.airbnb.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.main.airbnb.entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.expireTime}")
    private long expireTime;

    @Value("${jwt.issuer}")
    private String issuer;


    Algorithm algorithm;

    @PostConstruct
    public void generateAlgorithm(){
    algorithm = Algorithm.HMAC256(key);
    }

    public String generateToken(User user){
         return  JWT.create().withClaim("username",user.getUsername())
                 .withIssuer(issuer)
                .withExpiresAt(new Date(System.currentTimeMillis()+expireTime))
                .sign(algorithm);
    }

    public String getUsername(String token) {
     return  JWT.require(algorithm).withIssuer(issuer).build().verify(token).getClaim("username").asString();

    }
}
