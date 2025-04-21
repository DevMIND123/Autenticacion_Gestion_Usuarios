package com.autenticacion.demo.Service;

import com.autenticacion.demo.Dto.JwtAuthenticationResponse;
import com.autenticacion.demo.Dto.LoginRequestDTO;
import com.autenticacion.demo.Entities.Cliente;
import com.autenticacion.demo.Entities.Rol;
import com.autenticacion.demo.Repositories.AdministradorRepository;
import com.autenticacion.demo.Repositories.ClienteRepository;
import com.autenticacion.demo.Repositories.EmpresaRepository;
import com.autenticacion.demo.Services.JwtService;
import com.autenticacion.demo.Services.impl.AuthenticationServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private AdministradorRepository administradorRepository;

    @Mock
    private EmpresaRepository empresaRepository;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginWithCliente() {
        LoginRequestDTO request = new LoginRequestDTO("cliente@example.com", "password");
        Cliente cliente = new Cliente();
        cliente.setEmail("cliente@example.com");
        cliente.setRol(Rol.CLIENTE);
        cliente.setNombre("Cliente Test");

        when(clienteRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(cliente));
        when(jwtService.generateToken(cliente)).thenReturn("mocked-jwt-token");

        JwtAuthenticationResponse response = authenticationService.login(request);

        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.getToken());
        assertEquals("cliente@example.com", response.getEmail());
        assertEquals(Rol.CLIENTE, response.getRol());
        assertEquals("Cliente Test", response.getNombre());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void testLoginWithAdministrador() {
        LoginRequestDTO request = new LoginRequestDTO("admin@example.com", "password");
        when(clienteRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        var administrador = new com.autenticacion.demo.Entities.Administrador();
        administrador.setEmail("admin@example.com");
        administrador.setRol(Rol.ADMINISTRADOR);
        administrador.setNombre("Admin Test");

        when(administradorRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(administrador));
        when(jwtService.generateToken(administrador)).thenReturn("mocked-jwt-token");

        JwtAuthenticationResponse response = authenticationService.login(request);

        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.getToken());
        assertEquals("admin@example.com", response.getEmail());
        assertEquals(Rol.ADMINISTRADOR, response.getRol());
        assertEquals("Admin Test", response.getNombre());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void testLoginWithEmpresa() {
        LoginRequestDTO request = new LoginRequestDTO("empresa@example.com", "password");
        when(clienteRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(administradorRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        var empresa = new com.autenticacion.demo.Entities.Empresa();
        empresa.setEmail("empresa@example.com");
        empresa.setRol(Rol.EMPRESA);
        empresa.setNombreEmpresa("Empresa Test");

        when(empresaRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(empresa));
        when(jwtService.generateToken(empresa)).thenReturn("mocked-jwt-token");

        JwtAuthenticationResponse response = authenticationService.login(request);

        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.getToken());
        assertEquals("empresa@example.com", response.getEmail());
        assertEquals(Rol.EMPRESA, response.getRol());
        assertEquals("Empresa Test", response.getNombre());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void testLoginWithInvalidCredentials() {
        LoginRequestDTO request = new LoginRequestDTO("invalid@example.com", "password");
        when(clienteRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(administradorRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(empresaRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> authenticationService.login(request));

        assertEquals("Invalid email or password.", exception.getMessage());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
