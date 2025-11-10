package com.example.demo.auth.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.auth.entities.Session;
import com.example.demo.data.repositories.SessionRepository;

@Service
public class SessionService {

    @Autowired
    private SessionRepository repository;

    public void createSession(String token) {
        Session session = new Session(token);
        repository.createSession(session);
    }

    public Optional<Session> getSession(String token){
        return repository.getSession(token);
    }

    public boolean hasSessionExpired(Session session) {
        LocalDateTime createdAt = session.getCreatedAt();
        Duration diff = Duration.between(createdAt, LocalDateTime.now());
        if (diff.toMinutes() >= 120) {
            deleteSession(session);
            return true;
        }
        return false;
    }

    private void deleteSession(Session session) {
        repository.deleteSession(session);
    }
}
