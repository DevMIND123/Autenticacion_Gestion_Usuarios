package com.autenticacion.demo.Controllers;

import com.autenticacion.demo.Dto.CambioPasswordDTO;
import com.autenticacion.demo.Dto.ClienteActualizarDTO;
import com.autenticacion.demo.Dto.ClienteRegistroDTO;
import com.autenticacion.demo.Dto.ClienteRespuestaDTO;
import com.autenticacion.demo.Services.ClienteService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.retochimba.com/clientes")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteRespuestaDTO> registrarCliente(@RequestBody @Valid ClienteRegistroDTO dto) {
        logger.info("Registrando cliente con datos: {}", dto);
        return ResponseEntity.ok(clienteService.registrarCliente(dto));
    }

    @GetMapping
    public ResponseEntity<List<ClienteRespuestaDTO>> listarClientes() {
        logger.info("Listando todos los clientes");
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteRespuestaDTO> obtenerClientePorId(@PathVariable Long id) {
        logger.info("Obteniendo cliente con ID: {}", id);
        return ResponseEntity.ok(clienteService.obtenerClientePorId(id));
    }

    @GetMapping("/email/{email}")
    public Long obtenerIdClientePorEmail(@PathVariable String email) {
        return clienteService.obtenerIdClientePorEmail(email);
    }

    
    @PatchMapping("/actualizar/{id}")
    public ResponseEntity<Map<String, String>> actualizarCliente(
            @PathVariable Long id,
            @RequestBody @Valid ClienteActualizarDTO dto) {

        boolean actualizado = clienteService.actualizarCliente(id, dto);

        Map<String, String> response = new HashMap<>();

        if (actualizado) {
            response.put("mensaje", "Cliente actualizado correctamente.");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Cliente no encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/cambiar-password")
    public ResponseEntity<String> cambiarPassword(@RequestBody @Valid CambioPasswordDTO dto) {
        clienteService.cambiarPassword(dto);
        return ResponseEntity.ok("Contraseña actualizada correctamente.");
    }
}
