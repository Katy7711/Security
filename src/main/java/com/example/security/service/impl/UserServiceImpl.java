package com.example.security.service.impl;

import static com.example.security.entity.Role.ADMIN;

import com.example.security.entity.User;
import com.example.security.repository.UserRepository;
import com.example.security.service.UserService;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Data
@Transactional
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  //Сохранение пользователя
  @Override
  public void save(User user) {
    userRepository.save(user);
  }


  //Создание пользователя
  @Override
  public void create(User user) {
    if (userRepository.existsByUserName(user.getUsername())) {
      throw new RuntimeException("Пользователь с таким именем уже существует");
    }
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new RuntimeException("Пользователь с таким email уже существует");
    }
    save(user);
  }

  //Получение пользователя по имени пользователя
  @Override
  public User getByUsername(String username) {
    return userRepository.findByUserName(username)
        .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

  }

  //Получение пользователя по имени пользователя
  @Override
  public UserDetailsService userDetailsService() {
    return this::getByUsername;
  }

  //Получение текущего пользователя
  @Override
  public User getCurrentUser() {
    // Получение имени пользователя из контекста Spring Security
    var username = SecurityContextHolder.getContext().getAuthentication().getName();
    return getByUsername(username);
  }


  //Метод для демонстрации
  @Deprecated
  @Override
  public void getAdmin() {
    var user = getCurrentUser();
    user.setRole(ADMIN);
    save(user);
  }
}
