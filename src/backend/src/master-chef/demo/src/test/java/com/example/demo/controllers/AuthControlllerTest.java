package com.example.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.auth.AuthRequest;
import com.example.demo.auth.exceptions.UnmatchingUserCredentialsException;
import com.example.demo.auth.services.AuthService;
import com.example.demo.data.entities.User;
import com.example.demo.data.repositories.UserRepository;
import com.example.demo.model.dtos.UserDto;
import com.example.demo.model.exceptions.EntityNotFoundException;
import com.example.demo.model.exceptions.LoginAlreadyExistsException;
import com.example.demo.model.usecases.SignUpUseCase;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private SignUpUseCase signUpUseCase;

    @Mock
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    // --- TESTES DE SIGN UP ---

    @Test
    @DisplayName("Deve criar usuário com sucesso (200 OK)")
    void shouldSignUpSuccessfully() {
        // Arrange
        UserDto inputDto = mock(UserDto.class);
        UserDto createdDto = mock(UserDto.class);

        when(signUpUseCase.signUp(inputDto)).thenReturn(createdDto);

        // Act
        ResponseEntity<Map<String, Object>> response = authController.signUp(inputDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdDto, response.getBody().get("user"));
    }

    @Test
    @DisplayName("Deve retornar Bad Request quando login já existe (400 Bad Request)")
    void shouldReturnBadRequestWhenLoginExists() {
        // Arrange
        UserDto inputDto = mock(UserDto.class);
        String errorMessage = "Login already in use";

        when(signUpUseCase.signUp(any())).thenThrow(new LoginAlreadyExistsException(errorMessage));

        // Act
        ResponseEntity<Map<String, Object>> response = authController.signUp(inputDto);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().get("error_message"));
    }

    // --- TESTES DE SIGN IN ---

    @Test
    @DisplayName("Deve autenticar com sucesso e retornar token (200 OK)")
    void shouldSignInSuccessfully() {
        // Arrange
        AuthRequest request = mock(AuthRequest.class);
        String mockToken = "token-jwt-mock";
        String mockLogin = "userTest";

        // Mocks do fluxo de sucesso
        User userMock = mock(User.class);
        when(userMock.getLogin()).thenReturn(mockLogin);
        when(userMock.getRecipes()).thenReturn(new ArrayList<>()); // Evita erro no EntityMapper

        when(request.getLogin()).thenReturn(mockLogin);
        when(authService.getAuthenticationToken(request)).thenReturn(mockToken);
        when(userRepository.getUserByLogin(mockLogin)).thenReturn(userMock);

        // Act
        ResponseEntity<Map<String, Object>> response = authController.signIn(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifica Header de Autorização
        assertTrue(response.getHeaders().containsKey("Authorization"));
        assertEquals("Bearer " + mockToken, response.getHeaders().getFirst("Authorization"));

        // Verifica Body
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("user"));
    }

    @Test
    @DisplayName("Deve retornar Bad Request quando usuário não encontrado (400 Bad Request)")
    void shouldReturnBadRequestWhenUserNotFound() {
        // Arrange
        AuthRequest request = mock(AuthRequest.class);
        String errorMessage = "User not found";

        // Simula erro vindo do service ou repository
        when(authService.getAuthenticationToken(request)).thenThrow(new EntityNotFoundException(errorMessage));

        // Act
        ResponseEntity<Map<String, Object>> response = authController.signIn(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().get("error_message"));
    }

    @Test
    @DisplayName("Deve retornar Unauthorized quando credenciais inválidas (401 Unauthorized)")
    void shouldReturnUnauthorizedWhenCredentialsAreWrong() {
        // Arrange
        AuthRequest request = mock(AuthRequest.class);
        String errorMessage = "Invalid credentials";

        when(authService.getAuthenticationToken(request)).thenThrow(new UnmatchingUserCredentialsException(errorMessage));

        // Act
        ResponseEntity<Map<String, Object>> response = authController.signIn(request);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(errorMessage, response.getBody().get("error_message"));
    }
}