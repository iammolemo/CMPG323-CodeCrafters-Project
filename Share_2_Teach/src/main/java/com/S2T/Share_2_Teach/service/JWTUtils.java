package com.S2T.Share_2_Teach.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JWTUtils {

    private SecretKey Key; // Secret key used for signing JWT tokens

    private static final long EXPIRATION_TIME = 7200000L; // 2 hours in milliseconds

    // Constructor initializes the secret key for JWT signing
    public JWTUtils(){
        String secretString = "843567893696976453275974432697R634976R738467TR678T34865R6834R8767T47837863766453874567386578548735687R3";
        byte[] keyBytes = Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
        this.Key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    // Generates a JWT token for the given user details
    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername()) // Sets the subject (username) of the token
                .issuedAt(new Date(System.currentTimeMillis())) // Sets the issue date
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Sets the expiration date
                .signWith(Key) // Signs the token with the secret key
                .compact(); // Creates the JWT token
    }

    // Generates a refresh token with additional claims for the given user details
    public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails){
        return Jwts.builder()
                .claims(claims) // Adds claims to the token
                .subject(userDetails.getUsername()) // Sets the subject (username) of the token
                .issuedAt(new Date(System.currentTimeMillis())) // Sets the issue date
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Sets the expiration date
                .signWith(Key) // Signs the token with the secret key
                .compact(); // Creates the JWT token
    }

    // Extracts the username from the JWT token
    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject); // Extracts the subject (username) from claims
    }

    // Extracts specific claims from the JWT token using the provided function
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(Jwts.parser().setSigningKey(Key).build().parseClaimsJws(token).getBody()); // Parses and extracts claims from token
    }

    // Checks if the token is valid (i.e., if the username matches and the token is not expired)
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token); // Extracts the username from the token
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token); // Validates token
    }

    // Checks if the JWT token is expired
    public boolean isTokenExpired(String token){
        return extractClaims(token, Claims::getExpiration).before(new Date()); // Checks if the token's expiration date is before the current date
    }
}
