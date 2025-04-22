package com.autenticacion.demo.Service;

import com.autenticacion.demo.Dto.*;
import com.autenticacion.demo.Entities.Administrador;
import com.autenticacion.demo.Entities.Rol;
import com.autenticacion.demo.Repositories.AdministradorRepository;
import com.autenticacion.demo.Services.impl.AdministradorServiceImpl;
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

class AdministradorServiceTest {

    @InjectMocks
    private AdministradorServiceImpl administradorService;

    @Mock
    private AdministradorRepository administradorRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*
     * Caso de prueba para el método registrarAdministrador en la clase AdministradorService.
     * 
     * Este test verifica la funcionalidad de registrar un nuevo administrador. Asegura
     * que el servicio codifica correctamente la contraseña, guarda la entidad administrador, 
     * y devuelve el DTO de respuesta esperado con los datos correctos.
     * 
     * Pasos realizados en esta prueba:
     * 1. Crear un AdministradorRegistroDTO falso con datos de prueba.
     * 2. Construir un objeto Administrador con los valores esperados.
     * 3. Simular el comportamiento del codificador de contraseñas para que devuelva una contraseña codificada.
     * 4. Simular el comportamiento del repositorio para devolver el objeto Administrador guardado.
     * 5. Llamar al método registrarAdministrador y capturar la respuesta.
     * 6. Asegurar que la respuesta no es nula y contiene los datos esperados.
     * 7. Verificar que el método save del repositorio es llamado exactamente una vez.
     */
    @Test
    void registrarAdministrador() {
        AdministradorRegistroDTO dto = new AdministradorRegistroDTO("test@example.com", "password", "Test User", Rol.ADMINISTRADOR);
        Administrador administrador = Administrador.builder()
                .email(dto.getEmail())
                .password("encodedPassword")
                .nombre(dto.getNombre())
                .estadoCuenta("Activo")
                .rol(dto.getRol())
                .build();

        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");
        when(administradorRepository.save(any(Administrador.class))).thenReturn(administrador);

        AdministradorRespuestaDTO respuesta = administradorService.registrarAdministrador(dto);

        assertNotNull(respuesta);
        assertEquals(dto.getEmail(), respuesta.getEmail());
        assertEquals(dto.getNombre(), respuesta.getNombre());
        verify(administradorRepository, times(1)).save(any(Administrador.class));
    }

    /*
     * Método de prueba para obtenerIdAdministradorPorEmail(String)}.
     *
     * Este test verifica que el método obtiene correctamente el ID de un administrador
     * basado en su dirección de correo electrónico. Asegura que:
     * - El ID devuelto no es nulo.
     * - El ID devuelto coincide con el valor esperado.
     * - El método del repositorio `findByEmail` es llamado exactamente una vez con el email correcto.
     * 
     * Precondiciones:
     * - Existe un administrador con el email especificado en el repositorio.
     * 
     * Pasos de la prueba:
     * 1. Crear un objeto administrador simulado con un ID y un email predefinidos.
     * 2. Llame al método `findByEmail` del repositorio para que devuelva el administrador simulado.
     * 3. Llamar al método de servicio `obtenerIdAdministradorPorEmail` con el email.
     * 4. Comprobar que el ID devuelto no es nulo y coincide con el ID esperado.
     * 5. Verificar que el método `findByEmail` del repositorio es invocado una vez.
     */
    @Test
    void obtenerIdAdministradorPorEmail() {
        String email = "test@example.com";
        Administrador administrador = Administrador.builder().id(1L).email(email).build();

        when(administradorRepository.findByEmail(email)).thenReturn(Optional.of(administrador));

        Long id = administradorService.obtenerIdAdministradorPorEmail(email);

        assertNotNull(id);
        assertEquals(1L, id);
        verify(administradorRepository, times(1)).findByEmail(email);
    }

    /*
     * Caso de prueba para el método actualizarAdministrador en AdministradorService.
     *
     * Este test verifica que el método actualizarAdministrador actualiza correctamente
     * la información de un administrador en el repositorio y devuelve true cuando la
     * la actualización se realiza correctamente.
     * 
     * Pasos:
     * 1. Simular el método actualizarAdministrador del repositorio para que devuelva 1. * indicando que la actualización se ha realizado correctamente, 
     * indicando que la actualización se ha realizado correctamente.
     * 2. Llamar al método actualizarAdministrador en el servicio con un válido 
     * ID de administrador válido y un DTO que contenga la información actualizada.
     * 3. Comprobar que el método devuelve true, indicando que la actualización se ha realizado correctamente.
     * 4. Verificar que el método actualizarAdministrador del repositorio fue llamado 
     * exactamente una vez con los parámetros correctos.
     */
    @Test
    void actualizarAdministrador() {
        Long id = 1L;
        AdministradorActualizarDTO dto = new AdministradorActualizarDTO("Updated Name", "updated@example.com");

        when(administradorRepository.actualizarAdministrador(id, dto.getNombre(), dto.getEmail())).thenReturn(1);

        boolean actualizado = administradorService.actualizarAdministrador(id, dto);

        assertTrue(actualizado);
        verify(administradorRepository, times(1)).actualizarAdministrador(id, dto.getNombre(), dto.getEmail());
    }

    /*
     * Caso de prueba para el método eliminarAdministrador en AdministradorService.
     *
     * Esta prueba verifica que el método eliminarAdministrador invoca correctamente 
     * el método deleteById del administradorRepository con el ID especificado.
     * 
     * Pasos:
     * 1. Simular el comportamiento de administradorRepository para que no haga nada cuando se llama a deleteById.
     * 2. Llamar al método eliminarAdministrador con un ID de ejemplo.
     * 3. Verificar que el método deleteById de administradorRepository es llamado exactamente una vez con el ID dado.
     */
    @Test
    void eliminarAdministrador() {
        Long id = 1L;

        doNothing().when(administradorRepository).deleteById(id);

        administradorService.eliminarAdministrador(id);

        verify(administradorRepository, times(1)).deleteById(id);
    }

    /*
     * Caso de prueba para el método `cambiarContraseña` en la clase `AdministradorService`.
     *
     * Este test verifica la funcionalidad de cambiar la contraseña de un usuario. Asegura que:
     * - La entidad `Administrador` es recuperada correctamente del repositorio usando el email proporcionado.
     * - La nueva contraseña es codificada y actualizada en la entidad «Administrador».
     * - La entidad «Administrador» actualizada se guarda en el repositorio.
     * - La contraseña en la entidad «Administrador» coincide con la nueva contraseña codificada después de la operación.
     * - Los métodos del repositorio `findByEmail` y `save` son llamados el número esperado de veces.
     * 
     * Pasos de la prueba:
     * 1. Crear un objeto mock `CambioPasswordDTO` con el email del usuario y la nueva contraseña.
     * 2. 2. Crear un objeto mock `Administrador` con el email del usuario y la contraseña antigua.
     * 3. Simular los comportamientos del repositorio y del codificador para simular las interacciones esperadas.
     * 4. Llamar al método `cambiarContraseña` con el DTO.
     * 5. Asegurar que la contraseña en el objeto `Administrador` se actualiza a la nueva contraseña codificada.
     * 6. Verificar que los métodos del repositorio son llamados el número correcto de veces.
     */
    @Test
    void cambiarPassword() {
        String email = "test@example.com";
        CambioPasswordDTO dto = new CambioPasswordDTO(email, "newPassword");
        Administrador administrador = Administrador.builder().email(email).password("oldPassword").build();

        when(administradorRepository.findByEmail(email)).thenReturn(Optional.of(administrador));
        when(passwordEncoder.encode(dto.getNuevaPassword())).thenReturn("encodedNewPassword");
        when(administradorRepository.save(any(Administrador.class))).thenReturn(administrador);

        administradorService.cambiarPassword(dto);

        assertEquals("encodedNewPassword", administrador.getPassword());
        verify(administradorRepository, times(1)).findByEmail(email);
        verify(administradorRepository, times(1)).save(administrador);
    }
}