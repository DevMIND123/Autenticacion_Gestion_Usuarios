package com.autenticacion.demo.Controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.autenticacion.demo.Dto.CambioPasswordDTO;
import com.autenticacion.demo.Dto.ClienteActualizarDTO;
import com.autenticacion.demo.Dto.ClienteRegistroDTO;
import com.autenticacion.demo.Dto.ClienteRespuestaDTO;
import com.autenticacion.demo.Services.ClienteService;

class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    public ClienteControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Método de prueba para el punto final registrarCliente en ClienteController.
     * 
     * Esta prueba verifica lo siguiente:
     * - La respuesta del método registrarCliente no es nula.
     * - El cuerpo de la respuesta coincide con el objeto ClienteRespuestaDTO esperado.
     * - El método registrarCliente en clienteService es llamado exactamente una vez con el parámetro correcto.
     * 
     * La prueba utiliza Mockito para simular el comportamiento de clienteService y garantizar
     * que el método registrarCliente devuelve la respuesta esperada.
     */
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

    /**
     * Caso de prueba para el método obtenerIdClientePorEmail en ClienteController.
     * 
     * Este test verifica que el método obtiene correctamente el ID de un cliente
     * basado en su dirección de correo electrónico. Asegura que:
     * - El ID devuelto no es nulo.
     * - El ID devuelto coincide con el valor esperado.
     * - El método obtenerIdClientePorEmail del clienteService se llama exactamente una vez
     * con el parámetro de correo electrónico correcto.
     * 
     * Precondiciones:
     * Una implementación simulada de clienteService está configurada para devolver 
     * un ID de cliente predefinido cuando se obtiene el ID de cliente.
     * ID de cliente predefinido cuando el método obtenerIdClientePorEmail es llamado 
     * con un email específico.
     * 
     * Pasos de la prueba:
     * 1. Definir el email de entrada y el ID de cliente esperado.
     * 2. 2. Mock el comportamiento de clienteService para devolver el ID esperado.
     * 3. Llamar al método obtenerIdClientePorEmail de clienteController con el email de entrada.
     * 4. Comprobar que el ID devuelto no es nulo.
     * 5. 5. Comprobar que el ID devuelto coincide con el valor esperado.
     * 6. Verificar que el método obtenerIdClientePorEmail de clienteService es llamado una vez
     * con el correo electrónico correcto.
     */
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

    /**
     * Caso de prueba para el método {@code actualizarCliente} en el {@code ClienteController}.
     * 
     * Esta prueba verifica el comportamiento del controlador al actualizar un cliente.
     * Asegura que:
     * - La respuesta no es nula.
     * - El cuerpo de la respuesta contiene el mensaje de éxito esperado.
     * - El método de servicio {@code actualizarCliente} es llamado exactamente una vez con los parámetros correctos.
     * 
     * Escenario de prueba:
     * - Se configura un servicio simulado para que devuelva {@code true} cuando se llama al método actualizar.
     * - Se invoca al método controlador con un ID de cliente y un DTO de ejemplo.
     * - Se realizan aserciones para validar la respuesta y la interacción con el servicio.
     */
    @Test
    void actualizarClienteTest() {
        Long id = 1L;
        ClienteActualizarDTO dto = new ClienteActualizarDTO();

        when(clienteService.actualizarCliente(id, dto)).thenReturn(true);

        ResponseEntity<Map<String, String>> response = clienteController.actualizarCliente(id, dto);

        assertNotNull(response);
        assertEquals("Cliente actualizado correctamente.", response.getBody().get("mensaje"));
        verify(clienteService, times(1)).actualizarCliente(id, dto);
    }

    /**
     * Caso de prueba para el escenario en el que la actualización de un cliente falla porque el cliente
     * no se encuentra.
     *
     * Esta prueba verifica lo siguiente:
     * - El método de servicio `actualizarCliente` es llamado exactamente una vez con los parámetros correctos.
     * - La respuesta del controlador contiene el mensaje esperado «Cliente no encontrado».
     * - La respuesta no es nula.
     *
     * Mocks:
     * - `clienteService.actualizarCliente(id, dto)` es mockado para devolver `false`.
     */
    @Test
    void actualizarClienteNotFoundTest() {
        Long id = 1L;
        ClienteActualizarDTO dto = new ClienteActualizarDTO();

        when(clienteService.actualizarCliente(id, dto)).thenReturn(false);

        ResponseEntity<Map<String, String>> response = clienteController.actualizarCliente(id, dto);

        assertNotNull(response);
        assertEquals("Cliente no encontrado.", response.getBody().get("error"));
        verify(clienteService, times(1)).actualizarCliente(id, dto);
    }

    /**
     * Caso de prueba para el método eliminarCliente en ClienteController.
     * 
     * Este test verifica que el método eliminarCliente:
     * Llama al método eliminarCliente en clienteService exactamente una vez con el ID correcto.
     * - Devuelve una ResponseEntity con un código de estado 204 No Content.
     * - Asegura que la respuesta no es nula.
     * 
     * Precondiciones:
     * - Se proporciona un ID de cliente válido (1L en este caso).
     * El método clienteService.eliminarCliente no hace nada.
     * 
     * Comportamiento esperado:
     * - El método eliminarCliente en clienteService es invocado una vez con el ID proporcionado.
     * La respuesta del controlador es una ResponseEntity no nula con un código de estado 204.
     */
    @Test
    void eliminarClienteTest() {
        Long id = 1L;

        doNothing().when(clienteService).eliminarCliente(id);

        ResponseEntity<Void> response = clienteController.eliminarCliente(id);

        assertNotNull(response);
        assertEquals(204, response.getStatusCode().value());
        verify(clienteService, times(1)).eliminarCliente(id);
    }
    
    /**
     * Caso de prueba para el método {@code cambiarPassword} de la clase {@code ClienteController}.
     * 
     * Esta prueba verifica la ejecución exitosa de la funcionalidad de cambio de contraseña.
     * Asegura que:
     * - La respuesta no es nula.
     * - El cuerpo de la respuesta contiene el mensaje de éxito esperado.
     * - El método {@code cambiarPassword} del {@code ClienteService} se invoca exactamente una vez.
     * 
     * La prueba utiliza un objeto mock {@code CambioPasswordDTO} y mock el comportamiento del método
     * {@code clienteService.cambiarPassword} para no hacer nada.
     */
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
