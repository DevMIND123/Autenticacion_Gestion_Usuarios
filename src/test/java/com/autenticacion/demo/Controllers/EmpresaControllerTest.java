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

    @Test
    void testEliminarEmpresa() {
        Long id = 1L;

        ResponseEntity<Void> response = empresaController.eliminarEmpresa(id);

        assertNotNull(response);
        assertEquals(ResponseEntity.noContent().build(), response);
        verify(empresaService, times(1)).eliminarEmpresa(id);
    }

    @Test
    void testCambiarPassword() {
        CambioPasswordDTO dto = new CambioPasswordDTO();

        ResponseEntity<String> response = empresaController.cambiarPassword(dto);

        assertNotNull(response);
        assertEquals(ResponseEntity.ok("Contraseña actualizada correctamente."), response);
        verify(empresaService, times(1)).cambiarPassword(dto);
    }

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
