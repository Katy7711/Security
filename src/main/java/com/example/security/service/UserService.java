package com.example.security.service;


import com.example.security.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

  //Сохранение пользователя
  void save(User user);

  //Создание пользователя
  void create(User user);

  //Получение пользователя по имени пользователя
  User getByUsername(String username);

  //Получение пользователя по имени пользователя
  UserDetailsService userDetailsService();

  //Получение текущего пользователя
  User getCurrentUser();

  //Метод для демонстрации
  @Deprecated
  void getAdmin();
}



