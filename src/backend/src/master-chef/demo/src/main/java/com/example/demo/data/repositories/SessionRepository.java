package com.example.demo.data.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.auth.entities.Session;
import com.example.demo.data.interfaces.SessionRepositoryI;

@Service
public class SessionRepository {
    
    @Autowired
    private SessionRepositoryI repository;

    public Session createSession(Session session){
        return repository.save(session);
    }

    public void deleteSession(Session session){
        repository.delete(session);
    }

    public Optional<Session> getSession(String token){
        return repository.findById(token);
    }
}
