package com.autenticacion.demo.Controllers;

import com.autenticacion.demo.Dto.CambioPasswordDTO;
import com.autenticacion.demo.Dto.EmpresaActualizarDTO;
import com.autenticacion.demo.Dto.EmpresaRegistroDTO;
import com.autenticacion.demo.Dto.EmpresaRespuestaDTO;
import com.autenticacion.demo.Services.EmpresaService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmpresaControllerTest {

    @Mock
    private EmpresaService empresaService;

    @InjectMocks
    private EmpresaController empresaController;

    public EmpresaControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Caso de prueba para el método registrarEmpresa en la clase EmpresaController.
     * 
     * Esta prueba verifica lo siguiente:
     * El método registrarEmpresa del controlador devuelve una respuesta no nula.
     * La respuesta coincide con la ResponseEntity esperada que contiene el objeto EmpresaRespuestaDTO.
     * El método registrarEmpresa del EmpresaService se invoca exactamente una vez con el parámetro correcto.
     * 
     * Configuración de la prueba:
     * Se crea un objeto EmpresaRegistroDTO falso para simular los datos de entrada.
     * Se crea un objeto mock EmpresaRespuestaDTO para simular la respuesta del servicio.
     * Se simula el comportamiento del método registrarEmpresa del EmpresaService para devolver la respuesta simulada.
     * 
     * Afirmaciones:
     * La respuesta del controlador no es nula.
     * La respuesta coincide con la ResponseEntity esperada con el mock EmpresaRespuestaDTO.
     * El método registrarEmpresa del servicio se llama exactamente una vez con el mock EmpresaRegistroDTO.
     */
    @Test
    void testRegistrarEmpresa() {
        EmpresaRegistroDTO dto = new EmpresaRegistroDTO();
        EmpresaRespuestaDTO respuestaDTO = new EmpresaRespuestaDTO();
        when(empresaService.registrarEmpresa(dto)).thenReturn(respuestaDTO);

        ResponseEntity<EmpresaRespuestaDTO> response = empresaController.registrarEmpresa(dto);

        assertNotNull(response);
        assertEquals(ResponseEntity.ok(respuestaDTO), response);
        verify(empresaService, times(1)).registrarEmpresa(dto);
    }

    /**
     * Caso de prueba para el método {@code obtenerIdEmpresaPorEmail} en la clase 
     * {@code EmpresaController}.
     * 
     * Esta prueba verifica que el método obtiene correctamente el ID de una empresa
     * basándose en la dirección de correo electrónico proporcionada mediante:
     * - Simulando el comportamiento del {@code empresaService} para devolver un ID predefinido.
     * - Asegurando que el ID devuelto no es nulo.
     * - Comprobando que el ID devuelto coincide con el valor esperado.
     * - Verificar que el método {@code empresaService.obtenerIdEmpresaPorEmail} 
     * se llama exactamente una vez con el parámetro de correo electrónico correcto.
     */
    @Test
    void testObtenerIdEmpresaPorEmail() {
        String email = "test@example.com";
        Long expectedId = 1L;
        when(empresaService.obtenerIdEmpresaPorEmail(email)).thenReturn(expectedId);

        Long actualId = empresaController.obtenerIdEmpresaPorEmail(email);

        assertNotNull(actualId);
        assertEquals(expectedId, actualId);
        verify(empresaService, times(1)).obtenerIdEmpresaPorEmail(email);
    }

    /**
     * Caso de prueba para el método eliminarEmpresa en EmpresaController.
     * 
     * Esta prueba verifica que el método eliminarEmpresa:
     * 1. Devuelve un objeto ResponseEntity no nulo.
     * 2. Responde con un estado «204 No Content» cuando la eliminación se realiza correctamente.
     * 3. Invoca el método eliminarEmpresa del EmpresaService exactamente una vez con el ID correcto.
     */
    @Test
    void testEliminarEmpresa() {
        Long id = 1L;

        ResponseEntity<Void> response = empresaController.eliminarEmpresa(id);

        assertNotNull(response);
        assertEquals(ResponseEntity.noContent().build(), response);
        verify(empresaService, times(1)).eliminarEmpresa(id);
    }

    /**
     * Caso de prueba para el método cambiarContraseña de la clase EmpresaController.
     * 
     * Esta prueba verifica lo siguiente:
     * - La respuesta del método cambiarContraseña no es nula.
     * - La respuesta coincide con el mensaje de éxito esperado: «Contraseña actualizada correctamente».
     * - El método cambiarContraseña del EmpresaService se invoca exactamente una vez con el DTO proporcionado.
     */
    @Test
    void testCambiarPassword() {
        CambioPasswordDTO dto = new CambioPasswordDTO();

        ResponseEntity<String> response = empresaController.cambiarPassword(dto);

        assertNotNull(response);
        assertEquals(ResponseEntity.ok("Contraseña actualizada correctamente."), response);
        verify(empresaService, times(1)).cambiarPassword(dto);
    }

    /**
     * Caso de prueba del método {@code actualizarEmpresa} de la clase EmpresaController.
     * 
     * Esta prueba verifica el comportamiento del método al actualizar una empresa existente.
     * Asegura que la respuesta no es nula, el estado y el cuerpo de la respuesta son los esperados,
     * y el método de servicio {@code actualizarEmpresa} es llamado exactamente una vez con los parámetros correctos.
     * 
     * Escenario de prueba:
     * - Se proporciona un ID de empresa y un objeto EmpresaActualizarDTO válidos.
     * - Se espera que el método del controlador devuelva una respuesta correcta con un mensaje de confirmación.
     * - Se invoca a la capa de servicio para realizar la operación de actualización.
     */
    @Test
    void testActualizarEmpresa() {
        Long id = 1L;
        EmpresaActualizarDTO dto = new EmpresaActualizarDTO();

        ResponseEntity<String> response = empresaController.actualizarEmpresa(id, dto);

        assertNotNull(response);
        assertEquals(ResponseEntity.ok("Empresa actualizada correctamente."), response);
        verify(empresaService, times(1)).actualizarEmpresa(id, dto);
    }
}
