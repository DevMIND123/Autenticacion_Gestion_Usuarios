package com.autenticacion.demo.Services.impl;

import com.autenticacion.demo.Dto.CambioPasswordDTO;
import com.autenticacion.demo.Dto.EmpresaRegistroDTO;
import com.autenticacion.demo.Dto.EmpresaRespuestaDTO;
import com.autenticacion.demo.Entities.Empresa;
import com.autenticacion.demo.Repositories.EmpresaRepository;
import com.autenticacion.demo.Services.EmpresaService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
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
        try {
            // 1. Crear el usuario en Firebase
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(dto.getEmail())
                    .setPassword(dto.getPassword());

            FirebaseAuth.getInstance().createUser(request);

            // 2. Guardar en la base de datos
            Empresa empresa = Empresa.builder()
                    .tipoDocumento(dto.getTipoDocumento())
                    .numeroDocumento(dto.getNumeroDocumento())
                    .nombreEmpresa(dto.getNombreEmpresa())
                    .nombreRepresentante(dto.getNombreRepresentante())
                    .email(dto.getEmail())
                    .password(passwordEncoder.encode(dto.getPassword())) 
                    .estadoCuenta("Activo")
                    .build();

            Empresa guardada = empresaRepository.save(empresa);

            // 3. Retornar DTO
            return EmpresaRespuestaDTO.builder()
                    .id(guardada.getId())
                    .tipoDocumento(guardada.getTipoDocumento())
                    .numeroDocumento(guardada.getNumeroDocumento())
                    .nombreEmpresa(guardada.getNombreEmpresa())
                    .nombreRepresentante(guardada.getNombreRepresentante())
                    .email(guardada.getEmail())
                    .estadoCuenta(guardada.getEstadoCuenta())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("❌ Error al registrar empresa: " + e.getMessage());
        }
    }

    @Override
    public EmpresaRespuestaDTO obtenerEmpresaPorEmail(String email) {
    Empresa empresa = empresaRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Empresa no encontrada con el correo: " + email));

    return EmpresaRespuestaDTO.builder()
            .id(empresa.getId())
            .tipoDocumento(empresa.getTipoDocumento())
            .numeroDocumento(empresa.getNumeroDocumento())
            .nombreEmpresa(empresa.getNombreEmpresa())
            .nombreRepresentante(empresa.getNombreRepresentante())
            .email(empresa.getEmail())
            .estadoCuenta(empresa.getEstadoCuenta())
            .urlLogo(empresa.getUrlLogo())
            .build();
    }

    @Override
public void cambiarPassword(CambioPasswordDTO dto) {
    try {
        // Firebase
        UserRecord user = FirebaseAuth.getInstance().getUserByEmail(dto.getEmail());
        FirebaseAuth.getInstance().updateUser(
                new UserRecord.UpdateRequest(user.getUid()).setPassword(dto.getNuevaPassword())
        );

        // PostgreSQL
        Empresa empresa = empresaRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        empresa.setPassword(passwordEncoder.encode(dto.getNuevaPassword()));
        empresaRepository.save(empresa);

    } catch (Exception e) {
        throw new RuntimeException("Error al cambiar contraseña: " + e.getMessage());
        }
    }
}
