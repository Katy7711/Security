package com.example.security.service;

import static com.example.security.entity.Role.USER;

import com.example.security.dto.JwtAuthenticationResponse;
import com.example.security.dto.SignInRequest;
import com.example.security.dto.SignUpRequest;
import com.example.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserService userService;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  //Регистрация пользователя
  public JwtAuthenticationResponse signUp(SignUpRequest request) {

    UserDetails user = User.builder()
        .username(request.getUserName())
        .password(passwordEncoder.encode(request.getPassword()))
        .roles("USER")
        .build();

    com.example.security.entity.User user1 = new com.example.security.entity.User();
    user1.setUserName(request.getUserName());
    user1.setPassword(passwordEncoder.encode(request.getPassword()));
    user1.setEmail(request.getEmail());
    user1.setRole(USER);

    userService.create(user1);

    String jwt = jwtService.generateToken(user);
    return new JwtAuthenticationResponse(jwt);
  }

  //Аутентификация пользователя
  public JwtAuthenticationResponse signIn(SignInRequest request) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        request.getUserName(),
        request.getPassword()
    ));

    var user = userService
        .userDetailsService()
        .loadUserByUsername(request.getUserName());

    var jwt = jwtService.generateToken(user);
    return new JwtAuthenticationResponse(jwt);
  }
}
