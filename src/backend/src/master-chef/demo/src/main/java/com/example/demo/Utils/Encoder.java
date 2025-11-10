package com.example.demo.Utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Encoder {

    public static String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean verify(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
