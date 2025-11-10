package com.example.demo.model.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Utils.Encoder;
import com.example.demo.data.entities.User;
import com.example.demo.data.repositories.UserRepository;
import com.example.demo.model.dtos.UserDto;
import com.example.demo.model.exceptions.LoginAlreadyExistsException;

@Service
public class SignUpUseCase {

    @Autowired
    private UserRepository userRepository;

    public UserDto signUp(UserDto userDto) throws LoginAlreadyExistsException {
        if (userRepository.existsByLogin(userDto.getLogin())) {
            String message = String.format("Login %s is already being used", userDto.getLogin());
            throw new LoginAlreadyExistsException(message);
        }

        User user = new User(userDto.getLogin(), Encoder.encode(userDto.getPassword()), userDto.getName());
        User newUser = userRepository.createUser(user);

        return UserDto.builder()
                .id(newUser.getUserId())
                .login(newUser.getLogin())
                .name(newUser.getName())
                .build();
    }
}
