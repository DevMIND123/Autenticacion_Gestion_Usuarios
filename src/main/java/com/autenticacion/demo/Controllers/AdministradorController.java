package com.autenticacion.demo.Controllers;

import com.autenticacion.demo.Dto.AdministradorActualizarDTO;
import com.autenticacion.demo.Dto.AdministradorRegistroDTO;
import com.autenticacion.demo.Dto.AdministradorRespuestaDTO;
import com.autenticacion.demo.Dto.CambioPasswordDTO;
import com.autenticacion.demo.Services.AdministradorService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api.retochimba.com/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @PostMapping
    public ResponseEntity<AdministradorRespuestaDTO> registrarAdministrador(
            @RequestBody @Valid AdministradorRegistroDTO dto) {
        return ResponseEntity.ok(administradorService.registrarAdministrador(dto));
    }

    @GetMapping
    public ResponseEntity<List<AdministradorRespuestaDTO>> listarAdministradores() {
        return ResponseEntity.ok(administradorService.listarAdministradores());
    }
  
    @GetMapping("/{id}")
    public ResponseEntity<AdministradorRespuestaDTO> obtenerAdministradorPorId(@PathVariable Long id) {
        return ResponseEntity.ok(administradorService.obtenerAdministradorPorId(id));
    }

    @GetMapping("/email/{email}")
    public Long obtenerIdAdministradorPorEmail(@PathVariable String email) {
        return administradorService.obtenerIdAdministradorPorEmail(email);
    }

    @PatchMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarAdministrador(@PathVariable Long id, @RequestBody @Valid AdministradorActualizarDTO administrador) {
        boolean actualizado = administradorService.actualizarAdministrador(id, administrador);
        return actualizado ?
                ResponseEntity.ok("Administrador actualizado correctamente.") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Administrador no encontrado.");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarAdministrador(@PathVariable Long id) {
        administradorService.eliminarAdministrador(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/cambiar-password")
    public ResponseEntity<String> cambiarPassword(@RequestBody @Valid CambioPasswordDTO dto) {
        administradorService.cambiarPassword(dto);
        return ResponseEntity.ok("Contraseña actualizada correctamente.");
    }
}
