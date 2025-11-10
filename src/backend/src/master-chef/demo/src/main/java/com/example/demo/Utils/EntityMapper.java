package com.example.demo.Utils;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.data.entities.Recipe;
import com.example.demo.model.dtos.RecipeDto;
import com.example.demo.model.dtos.RecipeLineDto;

public class EntityMapper {

    public static RecipeDto toRecipeDto(Recipe recipe) {
        List<RecipeLineDto> linesDto = new ArrayList<>();

        recipe.getLines().stream().forEach(r -> {
            linesDto.add(new RecipeLineDto(r.getIngredient(), r.getAmount(), r.getUnit()));
        });

        RecipeDto recipeDto = new RecipeDto(recipe.getName(), linesDto);
        return recipeDto;
    }

    public static List<RecipeDto> toRecipeDtos(List<Recipe> recipes) {
        return recipes.stream().map(EntityMapper::toRecipeDto).toList();
    }
}
