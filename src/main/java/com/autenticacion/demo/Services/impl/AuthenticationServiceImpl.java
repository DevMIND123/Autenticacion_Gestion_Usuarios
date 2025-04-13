package com.autenticacion.demo.Services.impl;

import com.autenticacion.demo.Dto.*;
import com.autenticacion.demo.Entities.*;
import com.autenticacion.demo.Repositories.*;
import com.autenticacion.demo.Services.AuthenticationService;
import com.autenticacion.demo.Services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private AdministradorRepository administradorRepository;
    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    public JwtAuthenticationResponse login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        Cliente cliente = clienteRepository.findByEmail(request.getEmail())
                .orElse(null);
        if (cliente != null) {
            String jwt = jwtService.generateToken(cliente);
            return new JwtAuthenticationResponse(jwt, cliente.getEmail(), cliente.getRol(), cliente.getNombre());
        }
        Administrador administrador = administradorRepository.findByEmail(request.getEmail())
                .orElse(null);
        if (administrador != null) {
            String jwt = jwtService.generateToken(administrador);
            return new JwtAuthenticationResponse(jwt, administrador.getEmail(), administrador.getRol(), administrador.getNombre());
        }
        Empresa empresa = empresaRepository.findByEmail(request.getEmail())
                .orElse(null);
        if (empresa != null) {
            String jwt = jwtService.generateToken(empresa);
            return new JwtAuthenticationResponse(jwt, empresa.getEmail(), empresa.getRol(), empresa.getNombreEmpresa());
        } else {
            throw new IllegalArgumentException("Invalid email or password.");
        }
    }
}