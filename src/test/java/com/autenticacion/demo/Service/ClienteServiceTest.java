package com.autenticacion.demo.Service;

import com.autenticacion.demo.Dto.*;
import com.autenticacion.demo.Entities.Cliente;
import com.autenticacion.demo.Entities.Rol;
import com.autenticacion.demo.Repositories.ClienteRepository;
import com.autenticacion.demo.Services.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistrarCliente() {
        ClienteRegistroDTO dto = new ClienteRegistroDTO("test@example.com", "password123", "Test User", Rol.CLIENTE);
        Cliente cliente = Cliente.builder()
                .email(dto.getEmail())
                .password("encodedPassword")
                .nombre(dto.getNombre())
                .estadoCuenta("Activo")
                .rol(dto.getRol())
                .build();

        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteRespuestaDTO respuesta = clienteService.registrarCliente(dto);

        assertNotNull(respuesta);
        assertEquals(dto.getEmail(), respuesta.getEmail());
        assertEquals(dto.getNombre(), respuesta.getNombre());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testObtenerIdClientePorEmail() {
        String email = "test@example.com";
        Cliente cliente = Cliente.builder().id(1L).email(email).build();

        when(clienteRepository.findByEmail(email)).thenReturn(Optional.of(cliente));

        Long id = clienteService.obtenerIdClientePorEmail(email);

        assertNotNull(id);
        assertEquals(1L, id);
        verify(clienteRepository, times(1)).findByEmail(email);
    }

    @Test
    void testActualizarCliente() {
        Long id = 1L;
        ClienteActualizarDTO dto = new ClienteActualizarDTO("Updated User", "updated@example.com");

        when(clienteRepository.actualizarCliente(id, dto.getEmail())).thenReturn(1);

        boolean actualizado = clienteService.actualizarCliente(id, dto);

        assertTrue(actualizado);
        verify(clienteRepository, times(1)).actualizarCliente(id, dto.getEmail());
    }

    @Test
    void testEliminarCliente() {
        Long id = 1L;

        doNothing().when(clienteRepository).deleteById(id);

        clienteService.eliminarCliente(id);

        verify(clienteRepository, times(1)).deleteById(id);
    }

    @Test
    void testCambiarPassword() {
        String email = "test@example.com";
        CambioPasswordDTO dto = new CambioPasswordDTO(email, "newPassword123");
        Cliente cliente = Cliente.builder().email(email).password("oldPassword").build();

        when(clienteRepository.findByEmail(email)).thenReturn(Optional.of(cliente));
        when(passwordEncoder.encode(dto.getNuevaPassword())).thenReturn("encodedNewPassword");

        clienteService.cambiarPassword(dto);

        assertEquals("encodedNewPassword", cliente.getPassword());
        verify(clienteRepository, times(1)).save(cliente);
    }
}
