package com.autenticacion.demo.Services.impl;

import com.autenticacion.demo.Dto.*;
import com.autenticacion.demo.Entities.Empresa;
import com.autenticacion.demo.Entities.Rol;
import com.autenticacion.demo.Repositories.EmpresaRepository;
import com.autenticacion.demo.Services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public EmpresaRespuestaDTO registrarEmpresa(EmpresaRegistroDTO dto) {
        Empresa empresa = Empresa.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nombreEmpresa(dto.getNombreEmpresa())
                .nit(dto.getNit())
                .nombreRepresentante(dto.getNombreRepresentante())
                .direccion(dto.getDireccion())
                .telefono(dto.getTelefono())
                .estadoCuenta("Activo")
                .rol(Rol.EMPRESA)
                .build();

        Empresa guardada = empresaRepository.save(empresa);

        return EmpresaRespuestaDTO.builder()
                .id(guardada.getId())
                .nombreEmpresa(guardada.getNombreEmpresa())
                .nit(guardada.getNit())
                .nombreRepresentante(guardada.getNombreRepresentante())
                .email(guardada.getEmail())
                .direccion(guardada.getDireccion())
                .telefono(guardada.getTelefono())
                .estadoCuenta(guardada.getEstadoCuenta())
                .rol(dto.getRol())
                .build();
    }

    @Override
    public Long obtenerIdEmpresaPorEmail(String email) {
        return empresaRepository.findByEmail(email)
                .map(Empresa::getId)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
    }

    @Override
    public void eliminarEmpresa(Long id) {
        empresaRepository.deleteById(id);
    }

    @Override
    public void cambiarPassword(CambioPasswordDTO dto) {
        Empresa empresa = empresaRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
        empresa.setPassword(passwordEncoder.encode(dto.getNuevaPassword()));
        empresaRepository.save(empresa);
    }

    @Override
    public void actualizarEmpresa(Long id, EmpresaActualizarDTO dto) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada con ID: " + id));

        empresa.setNombreEmpresa(dto.getNombreEmpresa());
        empresa.setNit(dto.getNit());
        empresa.setNombreRepresentante(dto.getNombreRepresentante());
        empresa.setEmail(dto.getEmail());
        empresa.setDireccion(dto.getDireccion());
        empresa.setTelefono(dto.getTelefono());
        empresa.setRol(dto.getRol());

        empresaRepository.save(empresa);
    }

}