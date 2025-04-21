package com.autenticacion.demo.Service;

import com.autenticacion.demo.Dto.*;
import com.autenticacion.demo.Entities.Cliente;
import com.autenticacion.demo.Entities.Rol;
import com.autenticacion.demo.Repositories.ClienteRepository;
import com.autenticacion.demo.Services.ClienteService;
import com.autenticacion.demo.Services.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

    /**
     * Caso de prueba para el método (registrarCliente} en la clase {ClienteService}.
     * 
     * This test verifies the following:
     * 
     * A new client is successfully registered using the provided {ClienteRegistroDTO}.
     * La contraseña es codificada antes de guardar el cliente en el repositorio.
     * Se llama al método {save} del repositorio exactamente una vez.
     * El {ClienteRespuestaDTO} devuelto contiene el email y el nombre esperados.
     * 
     * Test Steps:
     * Crear un {ClienteRegistroDTO} con datos de prueba.
     * Mock the behavior of the {passwordEncoder} to return an encoded password.
     * Mock the behavior of the {clienteRepository} to return a {Cliente} object.
     * Call the {registrarCliente} method with the DTO.
     * Assert that the returned {ClienteRespuestaDTO} is not null and contains the expected data.
     * Verificar que el método {clienteRepository.save} es llamado una vez.
     */
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

    /**
     * Método de prueba para {@link ClienteService#obtenerIdClientePorEmail(String)}.
     * 
     * Este test verifica la funcionalidad del método que recupera el ID de un
     * cliente basado en su dirección de correo electrónico. Asegura que el método:
     * - Devuelve un ID no nulo cuando existe un cliente con el email dado.
     * - Devuelve el ID correcto asociado al correo electrónico.
     * - Llama al método del repositorio {@code findByEmail} exactamente una vez.
     * 
     * La prueba utiliza un repositorio simulado para simular la interacción con la base de datos y
     * valida el comportamiento del método de servicio.
     */
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

    /**
     * Caso de prueba para el método {@code actualizarCliente} de la clase {@code ClienteService}.
     * 
     * Esta prueba verifica que el método {@code actualizarCliente} actualiza correctamente la información de un cliente en el repositorio y devuelve un booleano indicando el éxito de la operación.
     * información de un cliente en el repositorio y devuelve un booleano indicando el éxito de la operación.
     * 
     * Pasos de la prueba:
     * 1. Definir un ID de cliente y un DTO que contenga la información actualizada del cliente.
     * 2. Mock el método del repositorio {@code actualizarCliente} para que devuelva un indicador de éxito (1).
     * 3. Llamar al método {@code actualizarCliente} en el servicio con el ID y el DTO proporcionados.
     * 4. Comprobar que el método devuelve {@code true}, indicando que la actualización se ha realizado correctamente.
     * 5. Verificar que el método del repositorio {@code actualizarCliente} fue llamado exactamente una vez
     * con los parámetros correctos.
     * 
     * Comportamiento esperado:
     * - El método de servicio debería devolver {@code true} cuando el repositorio indica una actualización exitosa.
     * - El método de repositorio debería ser invocado una vez con el ID de cliente y el email correctos.
     */
    @Test
    void testActualizarCliente() {
        Long id = 1L;
        ClienteActualizarDTO dto = new ClienteActualizarDTO("Updated User", "updated@example.com");

        when(clienteRepository.actualizarCliente(id, dto.getEmail())).thenReturn(1);

        boolean actualizado = clienteService.actualizarCliente(id, dto);

        assertTrue(actualizado);
        verify(clienteRepository, times(1)).actualizarCliente(id, dto.getEmail());
    }

    /**
     * Caso de prueba para el método {@code eliminarCliente} de la clase {@code ClienteService}.
     * 
     * Esta prueba verifica que el método {@code eliminarCliente} delega correctamente
     * la eliminación de un cliente al {@code clienteRepository} llamando a su método 
     * método {@code eliminarPorId} con el ID de cliente apropiado.
     * 
     * Pasos de la prueba:
     * 1. Simular el comportamiento de {@code clienteRepository.deleteById} para que no haga nada.
     * 2. Llame al método {@code eliminarCliente} con un ID de cliente de ejemplo.
     * 3. Verificar que el método {@code eliminarById} de {@code clienteRepository} 
     * es invocado exactamente una vez con el ID de cliente dado.
     */
    @Test
    void testEliminarCliente() {
        Long id = 1L;

        doNothing().when(clienteRepository).deleteById(id);

        clienteService.eliminarCliente(id);

        verify(clienteRepository, times(1)).deleteById(id);
    }

    /**
     * Caso de prueba para el método `cambiarContraseña` en la clase `ClienteService`.
     * 
     * Este test verifica lo siguiente:
     * 1. Se llama al método `findByEmail` de `clienteRepository` para recuperar la entidad
     * entidad `Cliente` asociada al email proporcionado.
     * 2. El método `passwordEncoder.encode` se utiliza para codificar la nueva contraseña.
     * 3. La contraseña de la entidad «Cliente» se actualiza con la contraseña codificada.
     * 4. La entidad `Cliente` actualizada se guarda utilizando el método `save` de `clienteRepository`.
     * 
     * Pasos de prueba:
     * - Se crea un objeto mock `Cliente` con una contraseña antigua.
     * - Se crea un objeto `CambioPasswordDTO` con el email y la nueva contraseña.
     * - Se definen comportamientos simulados para `clienteRepository.findByEmail` y 
     * `passwordEncoder.encode`.
     * - Se invoca al método `cambiarContraseña` con el DTO.
     * - Se hacen aserciones para asegurar que la contraseña se actualiza correctamente y se llama al método
     * - El método `save` es llamado exactamente una vez.
     */
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
