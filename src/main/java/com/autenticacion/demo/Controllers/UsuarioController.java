package com.autenticacion.demo.Controllers;

import com.autenticacion.demo.Dto.UsuarioRegistroDTO;
import com.autenticacion.demo.Dto.UsuarioRespuestaDTO;
import com.autenticacion.demo.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.retochimba.com/usuarios")
@CrossOrigin(origins = "*") // Para permitir peticiones desde el frontend
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioRespuestaDTO> registrarUsuario(@RequestBody UsuarioRegistroDTO dto) {
        UsuarioRespuestaDTO respuesta = usuarioService.registrarUsuario(dto);
        return ResponseEntity.ok(respuesta);
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioRespuestaDTO> obtenerPorEmail(@PathVariable String email) {
        UsuarioRespuestaDTO respuesta = usuarioService.obtenerUsuarioPorEmail(email);
        return ResponseEntity.ok(respuesta);
    }

}
