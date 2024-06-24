package com.example.security.service;

import com.example.security.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
  @Value("${token.signing.key}")
  private String jwtSigningKey;

  //Извлечение имени пользователя из токена
  public String extractUserName(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  //Генерация токена
  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    if (userDetails instanceof User customUserDetails) {
      claims.put("id", customUserDetails.getId());
      claims.put("email", customUserDetails.getEmail());
      claims.put("role", customUserDetails.getRole());
    }
    return generateToken(claims, userDetails);
  }

  //Проверка токена на валидность
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String userName = extractUserName(token);
    return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  //Извлечение данных из токена
  private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
    final Claims claims = extractAllClaims(token);
    return claimsResolvers.apply(claims);
  }

  //Генерация токена
  private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    return Jwts.builder().claims(extraClaims).subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
  }

  //Проверка токена на просроченность
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  //Извлечение даты истечения токена
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  //Извлечение всех данных из токена
  private Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(getSigningKey()).build().parseSignedClaims(token)
        .getPayload();
  }

  //Получение ключа для подписи токена
  private Key getSigningKey() {
    byte[] keyBytes = Base64.getDecoder().decode(jwtSigningKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

}
