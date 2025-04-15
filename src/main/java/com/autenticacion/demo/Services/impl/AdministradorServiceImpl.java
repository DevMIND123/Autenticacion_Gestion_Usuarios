package com.autenticacion.demo.Services.impl;

import com.autenticacion.demo.Dto.*;
import com.autenticacion.demo.Entities.Administrador;
import com.autenticacion.demo.Repositories.AdministradorRepository;
import com.autenticacion.demo.Services.AdministradorService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdministradorServiceImpl implements AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AdministradorRespuestaDTO registrarAdministrador(AdministradorRegistroDTO dto) {
        Administrador administrador = Administrador.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nombre(dto.getNombre())
                .estadoCuenta("Activo")
                .rol(dto.getRol())
                .build();

        Administrador guardado = administradorRepository.save(administrador);

        return AdministradorRespuestaDTO.builder()
                .id(guardado.getId())
                .nombre(guardado.getNombre())
                .email(guardado.getEmail())
                .estadoCuenta(guardado.getEstadoCuenta())
                .rol(guardado.getRol())
                .build();
    }

    @Override
    public Long obtenerIdAdministradorPorEmail(String email) {
        return administradorRepository.findByEmail(email)
                .map(Administrador::getId)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
    }

    @Override
    @Transactional
    public boolean actualizarAdministrador(Long id, AdministradorActualizarDTO dto) {
        return administradorRepository.actualizarAdministrador(id, dto.getNombre(), dto.getEmail()) > 0;
    }

    @Override
    public void eliminarAdministrador(Long id) {
        administradorRepository.deleteById(id);
    }

    @Override
    public void cambiarPassword(CambioPasswordDTO dto) {
        Administrador admin = administradorRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
        admin.setPassword(passwordEncoder.encode(dto.getNuevaPassword()));
        administradorRepository.save(admin);
    }
}