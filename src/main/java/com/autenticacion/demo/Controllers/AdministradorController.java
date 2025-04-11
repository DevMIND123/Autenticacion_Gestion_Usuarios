package com.autenticacion.demo.Controllers;

import com.autenticacion.demo.Dto.AdministradorActualizarDTO;
import com.autenticacion.demo.Dto.AdministradorRegistroDTO;
import com.autenticacion.demo.Dto.AdministradorRespuestaDTO;
import com.autenticacion.demo.Dto.CambioPasswordDTO;
import com.autenticacion.demo.Services.AdministradorService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.retochimba.com/administradores")
@CrossOrigin(origins = "*") // Para permitir peticiones desde el frontend
public class AdministradorController {

    private static final Logger logger = LoggerFactory.getLogger(AdministradorController.class);
    @Autowired
    private AdministradorService administradorService;

    @PostMapping
    public ResponseEntity<AdministradorRespuestaDTO> registrarAdministrador(
            @RequestBody @Valid AdministradorRegistroDTO dto) {
        AdministradorRespuestaDTO respuesta = administradorService.registrarAdministrador(dto);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AdministradorRespuestaDTO> obtenerPorEmail(@PathVariable String email) {
        AdministradorRespuestaDTO respuesta = administradorService.obtenerAdministradorPorEmail(email);
        return ResponseEntity.ok(respuesta);
    }

    @PatchMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarAdministrador(@PathVariable Long id,
            @RequestBody @Valid AdministradorActualizarDTO administrador) {
        logger.info("PATCH /actualizar/{} - Datos recibidos: {}", id, administrador);
        boolean respuesta = administradorService.actualizarAdministrador(id, administrador);
        if (respuesta) {
            return ResponseEntity.ok("Administrador actualizado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Administrador no encontrado.");
        }
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
