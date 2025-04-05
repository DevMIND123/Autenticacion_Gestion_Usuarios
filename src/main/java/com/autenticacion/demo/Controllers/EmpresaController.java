package com.autenticacion.demo.Controllers;

import com.autenticacion.demo.Dto.EmpresaRegistroDTO;
import com.autenticacion.demo.Dto.EmpresaRespuestaDTO;
import com.autenticacion.demo.Services.EmpresaService;
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
    public ResponseEntity<EmpresaRespuestaDTO> registrarEmpresa(@RequestBody EmpresaRegistroDTO dto) {
        EmpresaRespuestaDTO respuesta = empresaService.registrarEmpresa(dto);
        return ResponseEntity.ok(respuesta);
    }
}
