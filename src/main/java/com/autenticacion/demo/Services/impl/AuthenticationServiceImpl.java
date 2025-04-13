package com.autenticacion.demo.Services.impl;

import com.autenticacion.demo.Dto.*;
import com.autenticacion.demo.Entities.*;
import com.autenticacion.demo.Repositories.*;
import com.autenticacion.demo.Services.AuthenticationService;
import com.autenticacion.demo.Services.JwtService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public JwtAuthenticationResponse login(LoginRequestDTO request) {
        logger.info("PASOOOOOOOOOOOOOOOOOO");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
                logger.info("PASOOOOOOOOOOOOOOOOOO22");
                
        Cliente cliente = clienteRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        String jwt = jwtService.generateToken(cliente);
        return new JwtAuthenticationResponse(jwt, cliente.getEmail(), cliente.getRol());
    }
}