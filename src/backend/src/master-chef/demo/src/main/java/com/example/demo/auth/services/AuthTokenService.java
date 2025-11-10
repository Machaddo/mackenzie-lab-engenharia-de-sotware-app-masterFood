package com.example.demo.auth.services;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

@Service
public class AuthTokenService {

    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();
    private static final int LENGTH = 25;

    public String generateToken() {
        return random.ints(LENGTH, 0, CHAR_POOL.length())
                .mapToObj(CHAR_POOL::charAt)
                .collect(StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append)
                .toString();
    }
}
