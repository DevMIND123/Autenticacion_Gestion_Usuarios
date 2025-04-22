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
import static org.mockito.ArgumentMatchers.any;
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

    /**
     * Caso de prueba para la funcionalidad de inicio de sesión en el AuthenticationService.
     * 
     * Esta prueba verifica el comportamiento del método de inicio de sesión cuando una solicitud de inicio de sesión válida es realizada por un usuario con el rol CLIENTE.
     * es hecha por un usuario con el rol de CLIENTE. Asegura que:
     * 
     * Se consulta el clienteRepository para encontrar una entidad Cliente por correo electrónico.
     * El jwtService genera un token JWT para el cliente autenticado.
     * La respuesta contiene el token correcto, el correo electrónico, el rol y el nombre del cliente.
     * El authenticationManager es invocado para autenticar las credenciales del usuario.
     * 
     * Pasos de prueba:
     * 1. Crear un LoginRequestDTO con email y contraseña válidos.
     * 2. 2. Modelar el clienteRepository para que devuelva una entidad Cliente que coincida con el correo electrónico.
     * 3. Mock the jwtService para devolver un token JWT.
     * 4. Llamar al método login del authenticationService con la petición.
     * 5. Asegurar que la respuesta no es nula y contiene los valores esperados.
     * 6. Verificar que se llama al método authenticate del authenticationManager.
     */
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

    /**
     * Caso de prueba para la funcionalidad de inicio de sesión cuando el usuario es un administrador.
     * 
     * Esta prueba verifica lo siguiente:
     * La solicitud de acceso se procesa correctamente para un usuario administrador.
     * Los detalles del administrador son recuperados del repositorio.
     * Se genera un token JWT para el administrador.
     * La respuesta contiene el token, email, rol y nombre correctos.
     * Se invoca al gestor de autenticación para autenticar al usuario.
     * 
     * Pasos de prueba:
     * 1. Crear un DTO de petición de login con credenciales de administrador.
     * 2. Mock the `clienteRepository` to return an empty result for the email.
     * 3. Crear una entidad de administrador y simular el `administradorRepository` para devolverlo.
     * 4. Mock el `jwtService` para devolver un token JWT.
     * 5. Llamar al método `login` del `authenticationService`.
     * 6. Comprueba que la respuesta no es nula y contiene los valores esperados.
     * 7. Verificar que el `authenticationManager` es llamado con los argumentos correctos.
     */
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

    /**
     * Prueba unitaria para la funcionalidad de login del AuthenticationService cuando el usuario es una Empresa.
     * 
     * Esta prueba verifica lo siguiente:
     * El método de inicio de sesión identifica y autentica correctamente a un usuario Empresa.
     * El token JWT se genera y se devuelve como parte de la respuesta.
     * La respuesta contiene el correo electrónico, el rol y el nombre de la empresa correctos para el usuario de Empresa.
     * El proceso de autenticación se invoca utilizando el AuthenticationManager.
     * 
     * Pasos de prueba:
     * 1. Crear un LoginRequestDTO con el correo electrónico y la contraseña del usuario Empresa.
     * 2. Mock los repositorios para simular la ausencia de los usuarios Cliente y Administrador con el email dado.
     * 3. Simular el repositorio Empresa para devolver una entidad Empresa con el email dado.
     * 4. Simular el servicio JWT para devolver un token JWT simulado.
     * 5. Llamar al método login del AuthenticationService con la petición.
     * 6. Asegurar que la respuesta no es nula y contiene el token, email, rol y nombre de la empresa esperados.
     * 7. Verificar que se llama al método authenticate del AuthenticationManager.
     */
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

    /**
     * Prueba la funcionalidad de inicio de sesión con credenciales no válidas.
     * 
     * Esta prueba verifica que cuando se realiza una petición de login con un email que no
     * existe en ninguno de los repositorios (clienteRepository, administradorRepository, 
     * empresaRepository), el servicio lanza una IllegalArgumentException con el mensaje 
     * mensaje «Email o contraseña no válidos».
     * 
     * La prueba también asegura que el método authenticate del authenticationManager es 
     * invocado con un UsernamePasswordAuthenticationToken.
     * 
     * Precondiciones:
     * - El email proporcionado en el LoginRequestDTO no existe en ningún repositorio.
     * 
     * Comportamiento esperado:
     * - Se lanza una IllegalArgumentException con el mensaje «Invalid email or password».
     * - Se llama al método authenticate del authenticationManager.
     */
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
