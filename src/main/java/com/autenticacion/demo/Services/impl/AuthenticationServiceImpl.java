package com.autenticacion.demo.Services.impl;

import com.autenticacion.demo.Dto.*;
import com.autenticacion.demo.Entities.*;
import com.autenticacion.demo.Repositories.*;
import com.autenticacion.demo.Services.AuthenticationService;
import com.autenticacion.demo.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtService jwtService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private EmpresaRepository empresaRepository;
    @Autowired private AdministradorRepository administradorRepository;

    @Override
    public JwtAuthenticationResponse login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Buscar en cada tipo de usuario
        Cliente cliente = clienteRepository.findByEmail(request.getEmail()).orElse(null);
        if (cliente != null) {
            String token = jwtService.generateToken(cliente.getEmail());
            return JwtAuthenticationResponse.builder()
                    .token(token)
                    .email(cliente.getEmail())
                    .nombre(cliente.getNombre())
                    .rol(cliente.getRol())
                    .build();
        }

        Empresa empresa = empresaRepository.findByEmail(request.getEmail()).orElse(null);
        if (empresa != null) {
            String token = jwtService.generateToken(empresa.getEmail());
            return JwtAuthenticationResponse.builder()
                    .token(token)
                    .email(empresa.getEmail())
                    .nombre(empresa.getNombreEmpresa())
                    .rol(empresa.getRol())
                    .build();
        }

        Administrador admin = administradorRepository.findByEmail(request.getEmail()).orElse(null);
        if (admin != null) {
            String token = jwtService.generateToken(admin.getEmail());
            return JwtAuthenticationResponse.builder()
                    .token(token)
                    .email(admin.getEmail())
                    .nombre(admin.getNombre())
                    .rol(admin.getRol())
                    .build();
        }

        throw new UsernameNotFoundException("Usuario no encontrado");
    }
}