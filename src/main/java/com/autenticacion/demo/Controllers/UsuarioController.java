package com.autenticacion.demo.Controllers;

import com.autenticacion.demo.Dto.CambioPasswordDTO;
import com.autenticacion.demo.Dto.UsuarioActualizarDTO;
import com.autenticacion.demo.Dto.UsuarioRegistroDTO;
import com.autenticacion.demo.Dto.UsuarioRespuestaDTO;
import com.autenticacion.demo.Services.UsuarioService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.retochimba.com/usuarios")
@CrossOrigin(origins = "*") // Para permitir peticiones desde el frontend
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioRespuestaDTO> registrarUsuario(@RequestBody @Valid UsuarioRegistroDTO dto) {
        UsuarioRespuestaDTO respuesta = usuarioService.registrarUsuario(dto);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioRespuestaDTO> obtenerPorEmail(@PathVariable String email) {
        UsuarioRespuestaDTO respuesta = usuarioService.obtenerUsuarioPorEmail(email);
        return ResponseEntity.ok(respuesta);
    }

    @PatchMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarUsuario(@PathVariable Long id, @RequestBody @Valid UsuarioActualizarDTO usuario) {
        logger.info("PATCH /actualizar/{} - Datos recibidos: {}", id, usuario);
        boolean respuesta = usuarioService.actualizarUsuario(id, usuario);
        if (respuesta) {
            return ResponseEntity.ok("Usuario actualizado correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/cambiar-password")
    public ResponseEntity<String> cambiarPassword(@RequestBody @Valid CambioPasswordDTO dto) {
    usuarioService.cambiarPassword(dto);
    return ResponseEntity.ok("Contraseña actualizada correctamente.");
    }


}
