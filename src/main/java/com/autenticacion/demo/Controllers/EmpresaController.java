package com.autenticacion.demo.Controllers;

import com.autenticacion.demo.Dto.CambioPasswordDTO;
import com.autenticacion.demo.Dto.EmpresaActualizarDTO;
import com.autenticacion.demo.Dto.EmpresaRegistroDTO;
import com.autenticacion.demo.Dto.EmpresaRespuestaDTO;
import com.autenticacion.demo.Services.EmpresaService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.retochimba.com/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<EmpresaRespuestaDTO> registrarEmpresa(@RequestBody @Valid EmpresaRegistroDTO dto) {
        return ResponseEntity.ok(empresaService.registrarEmpresa(dto));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<EmpresaRespuestaDTO> obtenerEmpresaPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(empresaService.obtenerEmpresaPorEmail(email));
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
    public ResponseEntity<String> actualizarEmpresa(@PathVariable Long id, @RequestBody @Valid EmpresaActualizarDTO dto) {
        empresaService.actualizarEmpresa(id, dto);
        return ResponseEntity.ok("Empresa actualizada correctamente.");
    }
}
