package com.autenticacion.demo.Services.impl;

import com.autenticacion.demo.Dto.UsuarioRegistroDTO;
import com.autenticacion.demo.Dto.UsuarioRespuestaDTO;
import com.autenticacion.demo.Entities.Usuario;
import com.autenticacion.demo.Repositories.UsuarioRepository;
import com.autenticacion.demo.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;

import java.util.Date;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UsuarioRespuestaDTO registrarUsuario(UsuarioRegistroDTO dto) {
        try {
            // 1. Crear usuario en Firebase
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(dto.getEmail())
                    .setPassword(dto.getPassword());
    
            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
    
            // 2. Guardar en PostgreSQL
            Usuario usuario = Usuario.builder()
                    .nombre(dto.getNombre())
                    .email(dto.getEmail())
                    .password(dto.getPassword()) // puedes encriptarlo si quieres
                    .fechaRegistro(new Date())
                    .rol(dto.getRol())
                    .estadoCuenta("Activo")
                    .build();
    
            Usuario guardado = usuarioRepository.save(usuario);
    
            // 3. Retornar DTO
            return UsuarioRespuestaDTO.builder()
                    .id(guardado.getId())
                    .nombre(guardado.getNombre())
                    .email(guardado.getEmail())
                    .rol(guardado.getRol())
                    .estadoCuenta(guardado.getEstadoCuenta())
                    .build();
    
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar el usuario: " + e.getMessage());
        }
    }

    @Override
public UsuarioRespuestaDTO obtenerUsuarioPorEmail(String email) {
    Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    return UsuarioRespuestaDTO.builder()
            .id(usuario.getId())
            .nombre(usuario.getNombre())
            .email(usuario.getEmail())
            .rol(usuario.getRol())
            .estadoCuenta(usuario.getEstadoCuenta())
            .build();
    }
}