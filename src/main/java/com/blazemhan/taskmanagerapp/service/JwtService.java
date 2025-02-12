package com.blazemhan.taskmanagerapp.service;

import com.blazemhan.taskmanagerapp.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private String secretKey = null;


    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        String token = Jwts.
                builder()
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .issuer("Blazemhan")
                .signWith(generateKey())
                .compact();
        return token;
    }

    private SecretKey generateKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);

    }

    public String getSecretKey() {
        return secretKey = "ed5c5ff39bf25219b301904c2f43e8e6b99686916d0e" +
                "1bd067645ee60be5724d";
    }

    public boolean isTokenvalid(String token, UserDetails userDetails) {
        String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);

    }


    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);

    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}



