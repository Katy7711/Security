package com.example.security.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

//Авторизация пользователя
@Data
public class SignInRequest {
  @Size(min = 2, max = 50)
  @NotBlank()
  private String userName;

  @Size(min = 8, max = 255)
  @NotBlank()
  private String password;

}
