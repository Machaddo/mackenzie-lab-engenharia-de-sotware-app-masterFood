package com.example.demo.data.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.data.entities.Recipe;
import com.example.demo.data.entities.User;
import com.example.demo.data.interfaces.UserRepositoryI;

@Service
public class UserRepository {

    @Autowired
    private UserRepositoryI repository;

    public User createUser(User user) {
        return repository.save(user);
    }

    public boolean existsByLogin(String login) {
        return repository.existsByLogin(login);
    }

    public User getUserByLogin(String login){
        return repository.findUserByLogin(login);
    }

    public Optional<User> getUserById(UUID id){
        return repository.findById(id);
    }

    public List<Recipe> saveUserRecipe(UUID userId, Recipe recipe) {
        Optional<User> userOp = repository.findById(userId);
        if (!userOp.isPresent())
            return null;

        User user = userOp.get();
        recipe.setUser(user);
        user.addRecipe(recipe);
        User updatedUser = repository.save(user);
        return updatedUser.getRecipes();
    }
}