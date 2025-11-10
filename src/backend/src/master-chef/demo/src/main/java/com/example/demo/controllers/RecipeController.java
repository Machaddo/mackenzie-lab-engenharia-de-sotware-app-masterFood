package com.example.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Utils.JsonParsing;
import com.example.demo.adapters.agents.Agent;
import com.example.demo.adapters.filters.FilterManager;
import com.example.demo.adapters.filters.FilterManagerImpl;
import com.example.demo.adapters.filters.RecipeFilter;
import com.example.demo.model.dtos.RecipeDto;
import com.example.demo.model.exceptions.EntityNotFoundException;
import com.example.demo.model.exceptions.InvalidApiResponseException;
import com.example.demo.model.exceptions.InvalidUserPromptException;
import com.example.demo.model.usecases.GetUserRecipesUseCase;
import com.example.demo.model.usecases.SaveUserRecipeUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/receitas")
public class RecipeController {

    @Autowired
    private Agent chefAgent;

    @Autowired
    private SaveUserRecipeUseCase saveUserRecipe;

    @Autowired
    private GetUserRecipesUseCase getUserRecipes;

    @PostMapping()
    public ResponseEntity<Map<String, Object>> requestRecipe(@RequestBody String request) {
        Map<String, Object> response = new HashMap<>();

        try {
            FilterManager manager = new FilterManagerImpl(
                    List.of(new RecipeFilter(request)));

            if (!manager.applyFilters()) {
                response.put("error_message", "Prompt não condiz com o propósito do modelo");
                return ResponseEntity.badRequest().body(response);
            }

            String jsonResponse = chefAgent.get_receipts(request);
            List<RecipeDto> recipes = JsonParsing.parseRecipe(jsonResponse, RecipeDto.class);
            response.put("content", recipes);
            return ResponseEntity.ok(response);
        } catch (InvalidUserPromptException e) {
            response.put("error_message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (InvalidApiResponseException e) {
            response.put("error_message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            response.put("error_message", "Algo deu errado");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> saveUserRecipe(@PathVariable UUID userId,
            @Valid @RequestBody RecipeDto RecipeDto) {

        Map<String, Object> response = new HashMap<>();
        try {
            List<RecipeDto> recipes = saveUserRecipe.saveUserRecipe(userId, RecipeDto);
            response.put("recipes", recipes);
            return ResponseEntity.ok().body(response);
        } catch (EntityNotFoundException e) {
            response.put("error_message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getUserRecipes(@PathVariable UUID userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<RecipeDto> recipes = getUserRecipes.getUserRecipes(userId);
            response.put("recipes", recipes);
            return ResponseEntity.ok().body(response);
        } catch (EntityNotFoundException e) {
            response.put("error_message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
