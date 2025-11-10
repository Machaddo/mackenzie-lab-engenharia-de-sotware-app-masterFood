package com.example.demo.model.usecases;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Utils.EntityMapper;
import com.example.demo.data.entities.Recipe;
import com.example.demo.data.repositories.UserRepository;
import com.example.demo.model.dtos.RecipeDto;

import com.example.demo.model.exceptions.EntityNotFoundException;

@Service
public class SaveUserRecipeUseCase {

    @Autowired
    private UserRepository repository;

    public List<RecipeDto> saveUserRecipe(UUID userId, RecipeDto recipeDto) throws EntityNotFoundException {
        Recipe recipe = new Recipe(recipeDto.getName(), recipeDto.getLines());
        List<Recipe> userRecipes = repository.saveUserRecipe(userId, recipe);
        if (userRecipes == null) {
            String message = String.format("User %s não encontrado", userId.toString());
            throw new EntityNotFoundException(message);
        }

        return userRecipes.stream().map(EntityMapper::toRecipeDto).toList();
    }
}
