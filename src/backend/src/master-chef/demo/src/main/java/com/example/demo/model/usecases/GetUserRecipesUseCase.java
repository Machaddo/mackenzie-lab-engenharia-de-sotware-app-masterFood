package com.example.demo.model.usecases;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Utils.EntityMapper;
import com.example.demo.data.entities.Recipe;
import com.example.demo.data.entities.User;
import com.example.demo.data.repositories.UserRepository;
import com.example.demo.model.dtos.RecipeDto;
import com.example.demo.model.exceptions.EntityNotFoundException;

@Service
public class GetUserRecipesUseCase {

    @Autowired
    private UserRepository repository;

    public List<RecipeDto> getUserRecipes(UUID userId) throws EntityNotFoundException{
        Optional<User> user = repository.getUserById(userId);
        if (!user.isPresent())
            throw new EntityNotFoundException(String.format("Usuario %s não encontrado", userId));

        List<Recipe> recipes = user.get().getRecipes();
        return recipes.stream().map(EntityMapper::toRecipeDto).toList();
    }
}
