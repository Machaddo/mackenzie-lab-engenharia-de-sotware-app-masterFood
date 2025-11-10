package com.example.demo.data.interfaces;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.data.entities.User;

public interface UserRepositoryI extends JpaRepository<User, UUID>{
 
    boolean existsByLogin(String login);
    User findUserByLogin(String login);
}
