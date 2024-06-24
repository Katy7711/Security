package com.example.security.repository;

import com.example.security.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
  Optional<User> findByUserName(String userName);
  boolean existsByUserName(String userName);
  boolean existsByEmail(String email);
}
