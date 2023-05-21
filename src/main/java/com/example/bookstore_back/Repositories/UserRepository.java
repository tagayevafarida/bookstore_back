package com.example.bookstore_back.Repositories;


import com.example.bookstore_back.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    User findUserByEmail(String email);
    User findUserByToken(String token);
}
