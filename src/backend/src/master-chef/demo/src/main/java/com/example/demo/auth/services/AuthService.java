package com.example.demo.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Utils.Encoder;
import com.example.demo.auth.AuthRequest;
import com.example.demo.auth.exceptions.UnmatchingUserCredentialsException;
import com.example.demo.data.entities.User;
import com.example.demo.data.repositories.UserRepository;

import com.example.demo.model.exceptions.EntityNotFoundException;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionService sessionService;
    @Autowired
    private AuthTokenService authTokenService;

    public String getAuthenticationToken(AuthRequest request) throws UnmatchingUserCredentialsException,
            EntityNotFoundException {

        if (!userRepository.existsByLogin(request.getLogin())) {
            String m = String.format("Login %s não foi encontrao.", request.getLogin());
            throw new EntityNotFoundException(m);
        }

        User user = userRepository.getUserByLogin(request.getLogin());
        if (!Encoder.verify(request.getPassword(), user.getPassword()))
            throw new UnmatchingUserCredentialsException("Senha incorreta");

        String newToken = authTokenService.generateToken();
        sessionService.createSession(newToken);

        return newToken;
    }
}
