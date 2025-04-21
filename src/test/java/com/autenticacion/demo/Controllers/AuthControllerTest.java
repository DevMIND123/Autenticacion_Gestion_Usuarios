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
