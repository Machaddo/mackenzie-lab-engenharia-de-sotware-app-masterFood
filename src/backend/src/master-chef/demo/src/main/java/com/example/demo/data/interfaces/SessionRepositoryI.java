package com.example.demo.data.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.auth.entities.Session;

@Repository
public interface SessionRepositoryI extends JpaRepository<Session, String>{
    
}
