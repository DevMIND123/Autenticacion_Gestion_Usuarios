package com.autenticacion.demo.Controllers;

import com.autenticacion.demo.Dto.CambioPasswordDTO;
import com.autenticacion.demo.Dto.ClienteActualizarDTO;
import com.autenticacion.demo.Dto.ClienteRegistroDTO;
import com.autenticacion.demo.Dto.ClienteRespuestaDTO;
import com.autenticacion.demo.Services.ClienteService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.retochimba.com/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteRespuestaDTO> registrarCliente(@RequestBody @Valid ClienteRegistroDTO dto) {
        return ResponseEntity.ok(clienteService.registrarCliente(dto));
    }

    @GetMapping("/email/{email}")
    public Long obtenerIdClientePorEmail(@PathVariable String email) {
        return clienteService.obtenerIdClientePorEmail(email);
    }
    
    @PatchMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarCliente(@PathVariable Long id,
            @RequestBody @Valid ClienteActualizarDTO dto) {
        boolean actualizado = clienteService.actualizarCliente(id, dto);
        return actualizado ?
                ResponseEntity.ok("Cliente actualizado correctamente.") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado.");
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
