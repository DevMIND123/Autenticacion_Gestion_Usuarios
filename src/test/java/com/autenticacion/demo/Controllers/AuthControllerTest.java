package com.autenticacion.demo.Controllers;

import com.autenticacion.demo.Dto.JwtAuthenticationResponse;
import com.autenticacion.demo.Dto.LoginRequestDTO;
import com.autenticacion.demo.Services.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Prueba unitaria para la funcionalidad de inicio de sesión en el AuthController.
     * 
     * Esta prueba verifica que el método de login:
     * 1. Acepta un objeto LoginRequestDTO válido que contiene email y contraseña.
     * 2. 2. Llama al método de inicio de sesión del authenticationService exactamente una vez con la solicitud proporcionada.
     * 3. 3. Devuelve una ResponseEntity que contiene un JwtAuthenticationResponse con el token esperado.
     * 4. Responde con un código de estado HTTP 200 (OK).
     * 
     * Mocking se utiliza para simular el comportamiento del authenticationService y asegurar que
     * el controlador se comporta como se espera sin depender de dependencias externas.
     */
    @Test
    void testLogin() {
        // Arrange
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("testuser@probe.com");
        loginRequest.setPassword("password123");

        JwtAuthenticationResponse mockResponse = new JwtAuthenticationResponse();
        mockResponse.setToken("mockToken");

        when(authenticationService.login(loginRequest)).thenReturn(mockResponse);

        // Act
        ResponseEntity<JwtAuthenticationResponse> response = authController.login(loginRequest);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals("mockToken", response.getBody().getToken());
        verify(authenticationService, times(1)).login(loginRequest);
    }
}
