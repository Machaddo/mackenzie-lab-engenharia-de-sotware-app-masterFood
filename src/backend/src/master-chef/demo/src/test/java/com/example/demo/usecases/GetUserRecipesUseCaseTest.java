package com.example.demo.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.demo.model.usecases.GetUserRecipesUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.Utils.EntityMapper;
import com.example.demo.data.entities.Recipe;
import com.example.demo.data.entities.User;
import com.example.demo.data.repositories.UserRepository;
import com.example.demo.model.dtos.RecipeDto;
import com.example.demo.model.exceptions.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class GetUserRecipesUseCaseTest {

    @InjectMocks
    private GetUserRecipesUseCase getUserRecipesUseCase;

    @Mock
    private UserRepository repository;

    @Test
    @DisplayName("Deve retornar lista de receitas quando usuário existe")
    void shouldReturnRecipesWhenUserExists() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User userMock = mock(User.class);
        Recipe recipeMock = mock(Recipe.class);
        RecipeDto recipeDtoMock = new RecipeDto(); // Usando objeto real se for simples POJO, ou mock

        when(userMock.getRecipes()).thenReturn(List.of(recipeMock));
        when(repository.getUserById(userId)).thenReturn(Optional.of(userMock));

        // Mock do método estático EntityMapper
        try (MockedStatic<EntityMapper> mockedMapper = mockStatic(EntityMapper.class)) {
            mockedMapper.when(() -> EntityMapper.toRecipeDto(any(Recipe.class)))
                    .thenReturn(recipeDtoMock);

            // Act
            List<RecipeDto> result = getUserRecipesUseCase.getUserRecipes(userId);

            // Assert
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(recipeDtoMock, result.get(0));
        }
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando usuário não existe")
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        UUID userId = UUID.randomUUID();
        when(repository.getUserById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            getUserRecipesUseCase.getUserRecipes(userId);
        });

        assertEquals(String.format("Usuario %s não encontrado", userId), exception.getMessage());
    }
}