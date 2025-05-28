package com.autenticacion.demo.Services;

import com.autenticacion.demo.Dto.*;

public interface AuthenticationService {
    JwtAuthenticationResponse login(LoginRequestDTO request);
}