package com.autenticacion.demo.Service;

import com.autenticacion.demo.Dto.*;
import com.autenticacion.demo.Entities.Empresa;
import com.autenticacion.demo.Entities.Rol;
import com.autenticacion.demo.Repositories.EmpresaRepository;
import com.autenticacion.demo.Services.impl.EmpresaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmpresaServiceTest {

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

    @Test
    void testRegistrarEmpresa() {
        EmpresaRegistroDTO dto = new EmpresaRegistroDTO("test@example.com", "password", "Empresa Test", "123456789", "Representante Test", "Direccion Test", "123456789", Rol.EMPRESA);
        Empresa empresa = Empresa.builder()
                .email(dto.getEmail())
                .password("encodedPassword")
                .nombreEmpresa(dto.getNombreEmpresa())
                .nit(dto.getNit())
                .nombreRepresentante(dto.getNombreRepresentante())
                .direccion(dto.getDireccion())
                .telefono(dto.getTelefono())
                .estadoCuenta("Activo")
                .rol(Rol.EMPRESA)
                .build();

        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");
        when(empresaRepository.save(any(Empresa.class))).thenReturn(empresa);

        EmpresaRespuestaDTO respuesta = empresaService.registrarEmpresa(dto);

        assertNotNull(respuesta);
        assertEquals(dto.getEmail(), respuesta.getEmail());
        verify(empresaRepository, times(1)).save(any(Empresa.class));
    }

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

    @Test
    void testEliminarEmpresa() {
        Long id = 1L;

        doNothing().when(empresaRepository).deleteById(id);

        empresaService.eliminarEmpresa(id);

        verify(empresaRepository, times(1)).deleteById(id);
    }

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

    @Test
    void testActualizarEmpresa() {
        Long id = 1L;
        EmpresaActualizarDTO dto = new EmpresaActualizarDTO("Empresa Actualizada", "987654321", "Representante Actualizado", "actualizado@example.com", "Direccion Actualizada", "987654321", Rol.EMPRESA);
        Empresa empresa = Empresa.builder().id(id).build();

        when(empresaRepository.findById(id)).thenReturn(Optional.of(empresa));

        empresaService.actualizarEmpresa(id, dto);

        assertEquals(dto.getNombreEmpresa(), empresa.getNombreEmpresa());
        assertEquals(dto.getNit(), empresa.getNit());
        assertEquals(dto.getNombreRepresentante(), empresa.getNombreRepresentante());
        assertEquals(dto.getEmail(), empresa.getEmail());
        assertEquals(dto.getDireccion(), empresa.getDireccion());
        assertEquals(dto.getTelefono(), empresa.getTelefono());
        assertEquals(dto.getRol(), empresa.getRol());
        verify(empresaRepository, times(1)).save(empresa);
    }
}
