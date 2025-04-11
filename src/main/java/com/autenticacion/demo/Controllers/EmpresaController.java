package com.autenticacion.demo.Controllers;

import com.autenticacion.demo.Dto.CambioPasswordDTO;
import com.autenticacion.demo.Dto.EmpresaRegistroDTO;
import com.autenticacion.demo.Dto.EmpresaRespuestaDTO;
import com.autenticacion.demo.Services.EmpresaService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.retochimba.com/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<EmpresaRespuestaDTO> registrarEmpresa(@RequestBody @Valid EmpresaRegistroDTO dto) {
        EmpresaRespuestaDTO respuesta = empresaService.registrarEmpresa(dto);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<EmpresaRespuestaDTO> obtenerEmpresaPorEmail(@PathVariable String email) {
    EmpresaRespuestaDTO respuesta = empresaService.obtenerEmpresaPorEmail(email);
    return ResponseEntity.ok(respuesta);
    }

    @PatchMapping("/cambiar-password")
public ResponseEntity<String> cambiarPassword(@RequestBody @Valid CambioPasswordDTO dto) {
    empresaService.cambiarPassword(dto);
    return ResponseEntity.ok("Contraseña actualizada correctamente.");
    }
}
