package com.example.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.Utils.JsonParsing;
import com.example.demo.adapters.agents.Agent;
import com.example.demo.adapters.filters.FilterManager;
import com.example.demo.model.dtos.RecipeDto;
import com.example.demo.model.exceptions.EntityNotFoundException;
import com.example.demo.model.exceptions.InvalidApiResponseException;
import com.example.demo.model.exceptions.InvalidUserPromptException;
import com.example.demo.model.usecases.GetUserRecipesUseCase;
import com.example.demo.model.usecases.SaveUserRecipeUseCase;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @InjectMocks
    private RecipeController recipeController;

    @Mock
    private Agent chefAgent;

    @Mock
    private SaveUserRecipeUseCase saveUserRecipe;

    @Mock
    private GetUserRecipesUseCase getUserRecipes;

    @Mock
    private FilterManager filterManager;

    // --- TESTES DE REQUEST RECIPE (POST /receitas) ---

    @Test
    @DisplayName("Deve retornar receitas com sucesso (200 OK)")
    void shouldReturnRecipesSuccessfully() throws Exception {
        // Arrange
        String requestPrompt = "receita de bolo";
        String jsonResponse = "[{\"title\":\"Bolo\"}]";
        List<RecipeDto> expectedList = new ArrayList<>();
        expectedList.add(new RecipeDto());

        when(filterManager.applyFilters(anyList())).thenReturn(true);
        when(chefAgent.get_receipts(requestPrompt)).thenReturn(jsonResponse);

        // O mockStatic é necessário pois o controller chama um método estático (JsonParsing)
        try (MockedStatic<JsonParsing> mockedJsonParsing = mockStatic(JsonParsing.class)) {
            mockedJsonParsing.when(() -> JsonParsing.parseRecipe(eq(jsonResponse), eq(RecipeDto.class)))
                    .thenReturn(expectedList);

            // Act
            ResponseEntity<Map<String, Object>> response = recipeController.requestRecipe(requestPrompt);

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(expectedList, response.getBody().get("content"));
        }
    }

    @Test
    @DisplayName("Deve retornar Bad Request quando o filtro bloquear o prompt (400 Bad Request)")
    void shouldReturnBadRequestWhenFilterFails() throws Exception {
        // Arrange
        String requestPrompt = "prompt malicioso";
        when(filterManager.applyFilters(anyList())).thenReturn(false);

        // Act
        ResponseEntity<Map<String, Object>> response = recipeController.requestRecipe(requestPrompt);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Prompt não condiz com o propósito do modelo", response.getBody().get("error_message"));
    }

    @Test
    @DisplayName("Deve retornar Bad Request quando o prompt for inválido (Exception)")
    void shouldReturnBadRequestWhenPromptIsInvalid() throws Exception {
        // Arrange
        String errorMessage = "Prompt inválido";
        // Simulando erro na validação do filtro ou antes de chamar o agente
        when(filterManager.applyFilters(anyList())).thenThrow(new InvalidUserPromptException(errorMessage));

        // Act
        ResponseEntity<Map<String, Object>> response = recipeController.requestRecipe("teste");

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().get("error_message"));
    }

    @Test
    @DisplayName("Deve retornar Erro Interno quando a API do agente falhar (500 Internal Server Error)")
    void shouldReturnInternalErrorWhenAgentFails() throws Exception {
        // Arrange
        String errorMessage = "Erro na API externa";
        when(filterManager.applyFilters(anyList())).thenReturn(true);
        when(chefAgent.get_receipts(anyString())).thenThrow(new InvalidApiResponseException(errorMessage));

        // Act
        ResponseEntity<Map<String, Object>> response = recipeController.requestRecipe("teste");

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().get("error_message"));
    }

    @Test
    @DisplayName("Deve retornar Erro Interno para exceções genéricas (500 Internal Server Error)")
    void shouldReturnInternalErrorForGenericException() throws Exception {
        // Arrange
        when(filterManager.applyFilters(anyList())).thenThrow(new RuntimeException("Erro inesperado"));

        // Act
        ResponseEntity<Map<String, Object>> response = recipeController.requestRecipe("teste");

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Algo deu errado", response.getBody().get("error_message"));
    }

    // --- TESTES DE SAVE USER RECIPE (POST /receitas/{userId}) ---

    @Test
    @DisplayName("Deve salvar receita do usuário com sucesso (200 OK)")
    void shouldSaveUserRecipeSuccessfully() {
        // Arrange
        UUID userId = UUID.randomUUID();
        RecipeDto inputDto = mock(RecipeDto.class);
        List<RecipeDto> returnedList = new ArrayList<>();

        when(saveUserRecipe.saveUserRecipe(userId, inputDto)).thenReturn(returnedList);

        // Act
        ResponseEntity<Map<String, Object>> response = recipeController.saveUserRecipe(userId, inputDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(returnedList, response.getBody().get("recipes"));
    }

    @Test
    @DisplayName("Deve retornar Bad Request ao salvar se usuário não existir (400 Bad Request)")
    void shouldReturnBadRequestWhenSavingForUnknownUser() {
        // Arrange
        UUID userId = UUID.randomUUID();
        RecipeDto inputDto = mock(RecipeDto.class);
        String errorMessage = "User not found";

        when(saveUserRecipe.saveUserRecipe(userId, inputDto)).thenThrow(new EntityNotFoundException(errorMessage));

        // Act
        ResponseEntity<Map<String, Object>> response = recipeController.saveUserRecipe(userId, inputDto);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().get("error_message"));
    }

    // --- TESTES DE GET USER RECIPES (GET /receitas/{userId}) ---

    @Test
    @DisplayName("Deve buscar receitas do usuário com sucesso (200 OK)")
    void shouldGetUserRecipesSuccessfully() {
        // Arrange
        UUID userId = UUID.randomUUID();
        List<RecipeDto> expectedList = new ArrayList<>();

        when(getUserRecipes.getUserRecipes(userId)).thenReturn(expectedList);

        // Act
        ResponseEntity<Map<String, Object>> response = recipeController.getUserRecipes(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedList, response.getBody().get("recipes"));
    }

    @Test
    @DisplayName("Deve retornar Bad Request ao buscar receitas se usuário não existir (400 Bad Request)")
    void shouldReturnBadRequestWhenGettingRecipesForUnknownUser() {
        // Arrange
        UUID userId = UUID.randomUUID();
        String errorMessage = "User not found";

        when(getUserRecipes.getUserRecipes(userId)).thenThrow(new EntityNotFoundException(errorMessage));

        // Act
        ResponseEntity<Map<String, Object>> response = recipeController.getUserRecipes(userId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().get("error_message"));
    }
}