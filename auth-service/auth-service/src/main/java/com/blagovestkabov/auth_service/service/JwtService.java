package com.blagovestkabov.auth_service.service;

import com.blagovestkabov.auth_service.model.User;
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
        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuer("KABOV_BLG")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 10 * 60 * 500)) // 10 minutes
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey() {
        byte[] decode = Decoders.BASE64.decode(getSecretKey());
        return Keys.hmacShaKeyFor(decode);
    }

    public String getSecretKey() {
        return secretKey = "A_SECRET_KEY_TO_BE_CHANGED";
    }

    public String extractUsername(String jwtToken) {

        return claimsExtractor(jwtToken, Claims::getSubject);
    }

    private <T> T claimsExtractor(String jwtToken, Function<Claims, T> getClaims) {
        Claims claims = extractClaims(jwtToken);
        return getClaims.apply(claims);
    }

    private Claims extractClaims(String jwtToken) {
        return Jwts
                .parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String username = extractUsername(jwtToken);

        return username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        Date expirationTime = claimsExtractor(jwtToken, Claims::getExpiration);
        return expirationTime.before(new Date());
    }
}
