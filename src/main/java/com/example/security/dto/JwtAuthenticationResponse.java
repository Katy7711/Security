package com.example.security.dto;

import lombok.Builder;
import lombok.Data;


//Ответ с токеном доступа
@Data
@Builder
public class JwtAuthenticationResponse {
  private String token;

  public JwtAuthenticationResponse(String token) {
    this.token = token;
  }
}
