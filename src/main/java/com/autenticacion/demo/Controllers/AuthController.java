package com.autenticacion.demo.Controllers;

import com.autenticacion.demo.Dto.*;
import com.autenticacion.demo.Services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api.retochimba.com/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody @Valid LoginRequestDTO request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }
}