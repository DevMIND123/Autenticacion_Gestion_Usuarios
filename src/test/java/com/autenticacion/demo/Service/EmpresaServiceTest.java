package com.autenticacion.demo.Service;

import com.autenticacion.demo.Dto.*;
import com.autenticacion.demo.Entities.Empresa;
import com.autenticacion.demo.Entities.Rol;
import com.autenticacion.demo.Repositories.EmpresaRepository;
import com.autenticacion.demo.Services.KafkaProducerService;
import com.autenticacion.demo.Services.impl.EmpresaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

class EmpresaServiceTest {

    @Mock
    private KafkaProducerService kafkaProducer; // Mock del KafkaProducer

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EmpresaServiceImpl empresaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Caso de prueba para el método registrarEmpresa en la clase EmpresaService.
     * 
     * Esta prueba verifica la funcionalidad de registrar una nueva empresa. Asegura
     * que:
     * - El EmpresaRegistroDTO se mapea correctamente a una entidad Empresa.
     * - La contraseña es codificada antes de guardar la entidad.
     * - Se llama al EmpresaRepository para guardar la entidad.
     * - El EmpresaRespuestaDTO devuelto contiene el correo electrónico esperado.
     * 
     * Pasos de la prueba:
     * 1. Crear un mock EmpresaRegistroDTO con datos de ejemplo.
     * 2. 2. Mock the behavior of passwordEncoder.encode to return an encoded
     * password.
     * 3. Simular el comportamiento de empresaRepository.save para devolver un
     * objeto Empresa preconstruido.
     * 4. Llamar al método registrarEmpresa con el DTO.
     * 5. Compruebe que el objeto EmpresaRespuestaDTO devuelto no es nulo y contiene
     * el correo electrónico esperado.
     * 6. Verificar que el método empresaRepository.save fue llamado exactamente una
     * vez.
     * 
     * Resultado esperado:
     * El EmpresaRespuestaDTO se rellena correctamente con el correo electrónico del
     * DTO.
     * El método empresaRepository.save se invoca una vez.
     */
    @Test
    void testRegistrarEmpresa() {
        // Arrange
        EmpresaRegistroDTO dto = EmpresaRegistroDTO.builder()
                .email("nueva@empresa.com")
                .password("securePassword123")
                .nombreEmpresa("Nueva Empresa S.A.")
                .nit("987654321-0")
                .nombreRepresentante("María González")
                .direccion("Avenida Principal 456")
                .telefono("6012345678")
                .rol(Rol.EMPRESA)
                .build();

        String passwordEncoded = "$2a$10$encodedPasswordHash";

        // Empresa que se espera que se pase al repository.save()
        Empresa empresaEsperada = Empresa.builder()
                .email(dto.getEmail())
                .password(passwordEncoded)
                .nombreEmpresa(dto.getNombreEmpresa())
                .nit(dto.getNit())
                .nombreRepresentante(dto.getNombreRepresentante())
                .direccion(dto.getDireccion())
                .telefono(dto.getTelefono())
                .estadoCuenta("Activo")
                .rol(Rol.EMPRESA)
                .build();

        // Empresa que simula la guardada en BD (con ID generado)
        Empresa empresaGuardada = Empresa.builder()
                .id(99L)
                .email(dto.getEmail())
                .password(passwordEncoded)
                .nombreEmpresa(dto.getNombreEmpresa())
                .nit(dto.getNit())
                .nombreRepresentante(dto.getNombreRepresentante())
                .direccion(dto.getDireccion())
                .telefono(dto.getTelefono())
                .estadoCuenta("Activo")
                .rol(Rol.EMPRESA)
                .build();

        // Configurar mocks
        when(passwordEncoder.encode(dto.getPassword())).thenReturn(passwordEncoded);
        when(empresaRepository.save(any(Empresa.class))).thenReturn(empresaGuardada);

        // Act
        EmpresaRespuestaDTO resultado = empresaService.registrarEmpresa(dto);

        // Assert - Verificar el DTO de respuesta
        assertNotNull(resultado, "El DTO de respuesta no debería ser nulo");
        assertEquals(empresaGuardada.getId(), resultado.getId(), "El ID no coincide");
        assertEquals(dto.getEmail(), resultado.getEmail(), "El email no coincide");
        assertEquals(dto.getNombreEmpresa(), resultado.getNombreEmpresa(), "El nombre de empresa no coincide");
        assertEquals(dto.getNit(), resultado.getNit(), "El NIT no coincide");
        assertEquals(dto.getNombreRepresentante(), resultado.getNombreRepresentante(),
                "El nombre del representante no coincide");
        assertEquals(dto.getDireccion(), resultado.getDireccion(), "La dirección no coincide");
        assertEquals(dto.getTelefono(), resultado.getTelefono(), "El teléfono no coincide");
        assertEquals("Activo", resultado.getEstadoCuenta(), "El estado de cuenta debería ser 'Activo'");
        assertEquals(Rol.EMPRESA, resultado.getRol(), "El rol debería ser EMPRESA");

        // Verificar interacciones con los mocks
        verify(passwordEncoder, times(1)).encode(dto.getPassword());
        verify(empresaRepository, times(1)).save(empresaEsperada);

        // Verificar mensaje Kafka
        String expectedKafkaMessage = String.format(
                "{\"email\": \"%s\", \"nombre\": \"%s\", \"tipo\": \"%s\"}",
                dto.getEmail(),
                dto.getNombreEmpresa(),
                Rol.EMPRESA.name());
        verify(kafkaProducer, times(1)).enviarMensaje(expectedKafkaMessage);
    }

    /**
     * Método de prueba para
     * com.autenticacion.demo.Service.EmpresaService#obtenerIdEmpresaPorEmail(String).
     * 
     * Este test verifica que el método obtenerIdEmpresaPorEmail recupera
     * correctamente
     * el ID de una entidad Empresa a partir de la dirección de correo electrónico
     * proporcionada.
     * 
     * Pasos realizados:
     * - Se crea un objeto Empresa simulado con un ID y un email específicos.
     * - Se utiliza el método empresaRepository.findByEmail para devolver el objeto
     * Empresa.
     * - Se llama al método obtenerIdEmpresaPorEmail con el email.
     * - Se realizan aserciones para asegurar que el ID devuelto es correcto y no
     * nulo.
     * - Se realiza una verificación para asegurar que el método del repositorio fue
     * llamado exactamente una vez.
     * 
     * Comportamiento esperado:
     * - El ID devuelto coincide con el ID de la Empresa simulada.
     * - El método findByEmail del repositorio se invoca exactamente una vez.
     */
    @Test
    void testObtenerIdEmpresaPorEmail() {
        String email = "test@example.com";
        Empresa empresa = Empresa.builder().id(1L).email(email).build();

        when(empresaRepository.findByEmail(email)).thenReturn(Optional.of(empresa));

        Long id = empresaService.obtenerIdEmpresaPorEmail(email);

        assertNotNull(id);
        assertEquals(1L, id);
        verify(empresaRepository, times(1)).findByEmail(email);
    }

    /**
     * Prueba unitaria para el método eliminarEmpresa en EmpresaService.
     * 
     * Este test verifica que el método eliminarEmpresa interactúa correctamente con
     * el empresaRepository para eliminar una entidad por su ID. Asegura que el
     * método
     * deleteById del repositorio es llamado exactamente una vez con el ID esperado.
     * 
     * Pasos de la prueba:
     * 1. Simular el comportamiento de empresaRepository para que no haga nada
     * cuando se llama a deleteById.
     * 2. Llamar al método eliminarEmpresa con un ID de ejemplo.
     * 3. Verificar que el método deleteById de empresaRepository es invocado una
     * vez con el ID correcto.
     */
    @Test
    void testEliminarEmpresa() {
        Long id = 1L;

        doNothing().when(empresaRepository).deleteById(id);

        empresaService.eliminarEmpresa(id);

        verify(empresaRepository, times(1)).deleteById(id);
    }

    /**
     * Prueba unitaria para el método {@code cambiarContraseña} de la clase
     * {@code EmpresaService}.
     * 
     * Esta prueba verifica la funcionalidad de cambiar la contraseña de un usuario.
     * Asegura que:
     * 1. El repositorio recupera la entidad {@code Empresa} correcta por correo
     * electrónico.
     * 2. El codificador de contraseñas codifica la nueva contraseña correctamente.
     * 3. La contraseña actualizada se guarda en el repositorio.
     * 4. La contraseña de la entidad {@code Empresa} se actualiza con la nueva
     * contraseña codificada.
     * 
     * Pasos de prueba:
     * - Crear un objeto {@code CambioPasswordDTO} con el email y la nueva
     * contraseña.
     * - Simular el repositorio para que devuelva una entidad {@code Empresa} con el
     * correo electrónico dado.
     * - Simular el codificador de contraseñas para devolver una versión codificada
     * de la nueva contraseña.
     * - Llamar al método {@code cambiarContraseña} con el DTO.
     * - Asegurar que la contraseña en la entidad {@code Empresa} se actualiza a la
     * contraseña codificada.
     * - Verificar que el método guardar del repositorio es llamado exactamente una
     * vez.
     */
    @Test
    void testCambiarPassword() {
        CambioPasswordDTO dto = new CambioPasswordDTO("test@example.com", "newPassword");
        Empresa empresa = Empresa.builder().email(dto.getEmail()).password("oldPassword").build();

        when(empresaRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(empresa));
        when(passwordEncoder.encode(dto.getNuevaPassword())).thenReturn("encodedNewPassword");

        empresaService.cambiarPassword(dto);

        assertEquals("encodedNewPassword", empresa.getPassword());
        verify(empresaRepository, times(1)).save(empresa);
    }

    /**
     * Prueba unitaria para el método actualizarEmpresa en EmpresaService.
     * 
     * Esta prueba verifica que el método actualizarEmpresa actualiza correctamente
     * los atributos de una entidad Empresa existente basándose en el objeto
     * EmpresaActualizarDTO proporcionado. También asegura que la entidad
     * actualizada
     * se guarda en el repositorio.
     * 
     * Pasos de la prueba:
     * 1. Crear una entidad Empresa simulada con un ID específico.
     * 2. 2. Mock the empresaRepository para que devuelva el mock Empresa cuando se
     * llame a findById.
     * 3. Llamar al método actualizarEmpresa con el ID y EmpresaActualizarDTO.
     * 4. Comprobar que los atributos de la entidad Empresa se actualizan para
     * coincidir con
     * los valores de EmpresaActualizarDTO.
     * 5. Verificar que el método save de empresaRepository se llama exactamente una
     * vez.
     * 
     * Afirmaciones:
     * - Los atributos de la entidad Empresa (nombreEmpresa, nit,
     * nombreRepresentante,
     * email) deben coincidir con los valores del DTO.
     * - El método save de empresaRepository debe invocarse una vez.
     */
    @Test
    void testActualizarEmpresa() {
        Long id = 1L;
        EmpresaActualizarDTO dto = new EmpresaActualizarDTO("Empresa Actualizada", "987654321",
                "Representante Actualizado", "actualizado@example.com");
        Empresa empresa = Empresa.builder().id(id).build();

        when(empresaRepository.findById(id)).thenReturn(Optional.of(empresa));

        empresaService.actualizarEmpresa(id, dto);

        assertEquals(dto.getNombreEmpresa(), empresa.getNombreEmpresa());
        assertEquals(dto.getNit(), empresa.getNit());
        assertEquals(dto.getNombreRepresentante(), empresa.getNombreRepresentante());
        assertEquals(dto.getEmail(), empresa.getEmail());
        // assertEquals(dto.getDireccion(), empresa.getDireccion());
        // assertEquals(dto.getTelefono(), empresa.getTelefono());
        // assertEquals(dto.getRol(), empresa.getRol());
        verify(empresaRepository, times(1)).save(empresa);
    }
}
