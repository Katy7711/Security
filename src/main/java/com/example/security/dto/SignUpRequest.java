package com.example.security.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

//Регистрация пользователя
@Data
public class SignUpRequest {
  @Size(min = 2, max = 50)
  @NotBlank()
  private String username;

  @Size(min = 5, max = 255)
  @NotBlank()
  private String email;

  @Size(max = 255)
  private String password;

}
