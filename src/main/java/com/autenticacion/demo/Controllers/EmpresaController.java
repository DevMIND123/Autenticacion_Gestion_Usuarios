package com.autenticacion.demo.Controllers;

import com.autenticacion.demo.Dto.CambioPasswordDTO;
import com.autenticacion.demo.Dto.EmpresaActualizarDTO;
import com.autenticacion.demo.Dto.EmpresaRegistroDTO;
import com.autenticacion.demo.Dto.EmpresaRespuestaDTO;
import com.autenticacion.demo.Services.EmpresaService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.retochimba.com/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<EmpresaRespuestaDTO> registrarEmpresa(@RequestBody @Valid EmpresaRegistroDTO dto) {
        return ResponseEntity.ok(empresaService.registrarEmpresa(dto));
    }

    @GetMapping
    public ResponseEntity<List<EmpresaRespuestaDTO>> listarEmpresas() {
        return ResponseEntity.ok(empresaService.obtenerEmpresas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaRespuestaDTO> obtenerEmpresaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(empresaService.obtenerEmpresaPorId(id));
    }

    @GetMapping("/email/{email}")
    public Long obtenerIdEmpresaPorEmail(@PathVariable String email) {
        return empresaService.obtenerIdEmpresaPorEmail(email);
    }
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable Long id) {
        empresaService.eliminarEmpresa(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/cambiar-password")
    public ResponseEntity<String> cambiarPassword(@RequestBody @Valid CambioPasswordDTO dto) {
        empresaService.cambiarPassword(dto);
        return ResponseEntity.ok("Contraseña actualizada correctamente.");
    }

    @PatchMapping("/actualizar/{id}")
    public ResponseEntity<Map<String, String>> actualizarEmpresa(
            @PathVariable Long id,
            @RequestBody @Valid EmpresaActualizarDTO dto) {

        empresaService.actualizarEmpresa(id, dto);

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Empresa actualizada correctamente.");
        return ResponseEntity.ok(response);
    }

}
