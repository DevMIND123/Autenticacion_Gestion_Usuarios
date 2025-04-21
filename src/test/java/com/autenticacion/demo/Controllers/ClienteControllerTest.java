package com.autenticacion.demo.Controllers;

import com.autenticacion.demo.Dto.CambioPasswordDTO;
import com.autenticacion.demo.Dto.ClienteActualizarDTO;
import com.autenticacion.demo.Dto.ClienteRegistroDTO;
import com.autenticacion.demo.Dto.ClienteRespuestaDTO;
import com.autenticacion.demo.Services.ClienteService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    public ClienteControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarClienteTest() {
        ClienteRegistroDTO dto = new ClienteRegistroDTO();
        ClienteRespuestaDTO respuestaDTO = new ClienteRespuestaDTO();

        when(clienteService.registrarCliente(dto)).thenReturn(respuestaDTO);

        ResponseEntity<ClienteRespuestaDTO> response = clienteController.registrarCliente(dto);

        assertNotNull(response);
        assertEquals(respuestaDTO, response.getBody());
        verify(clienteService, times(1)).registrarCliente(dto);
    }

    @Test
    void obtenerIdClientePorEmailTest() {
        String email = "test@example.com";
        Long expectedId = 1L;

        when(clienteService.obtenerIdClientePorEmail(email)).thenReturn(expectedId);

        Long actualId = clienteController.obtenerIdClientePorEmail(email);

        assertNotNull(actualId);
        assertEquals(expectedId, actualId);
        verify(clienteService, times(1)).obtenerIdClientePorEmail(email);
    }

    @Test
    void actualizarClienteTest() {
        Long id = 1L;
        ClienteActualizarDTO dto = new ClienteActualizarDTO();

        when(clienteService.actualizarCliente(id, dto)).thenReturn(true);

        ResponseEntity<String> response = clienteController.actualizarCliente(id, dto);

        assertNotNull(response);
        assertEquals("Cliente actualizado correctamente.", response.getBody());
        verify(clienteService, times(1)).actualizarCliente(id, dto);
    }

    @Test
    void actualizarClienteNotFoundTest() {
        Long id = 1L;
        ClienteActualizarDTO dto = new ClienteActualizarDTO();

        when(clienteService.actualizarCliente(id, dto)).thenReturn(false);

        ResponseEntity<String> response = clienteController.actualizarCliente(id, dto);

        assertNotNull(response);
        assertEquals("Cliente no encontrado.", response.getBody());
        verify(clienteService, times(1)).actualizarCliente(id, dto);
    }

    @Test
    void eliminarClienteTest() {
        Long id = 1L;

        doNothing().when(clienteService).eliminarCliente(id);

        ResponseEntity<Void> response = clienteController.eliminarCliente(id);

        assertNotNull(response);
        assertEquals(204, response.getStatusCode().value());
        verify(clienteService, times(1)).eliminarCliente(id);
    }

    @Test
    void cambiarPasswordTest() {
        CambioPasswordDTO dto = new CambioPasswordDTO();

        doNothing().when(clienteService).cambiarPassword(dto);

        ResponseEntity<String> response = clienteController.cambiarPassword(dto);

        assertNotNull(response);
        assertEquals("Contraseña actualizada correctamente.", response.getBody());
        verify(clienteService, times(1)).cambiarPassword(dto);
    }
}
