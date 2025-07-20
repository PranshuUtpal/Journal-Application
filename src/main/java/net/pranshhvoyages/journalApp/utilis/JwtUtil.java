package net.pranshhvoyages.journalApp.utilis;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.security.auth.Subject;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
//@Getter
//@Setter
public class JwtUtil {

    private String SECRET_KEY = "Chand*Tare*Ful*Sabnam*Tumse*Achha#Kon&Hai#NoOne@!!!";

    private SecretKey getSigninKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

    public String extractClaim(String token, String claim){
        return extractAllClaims(token).get(claim, String.class);
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }


    public String generateTocken(String username){
        Map<String, Object> claims = new HashMap<>();
        return  createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10)) // 10 minuts
                .signWith(getSigninKey())
                .compact();
    }

    public boolean validateToken(String token){
        return !isTokenExpired(token);
    }
}
