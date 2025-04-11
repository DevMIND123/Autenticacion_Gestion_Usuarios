package com.autenticacion.demo.Services.impl;

import com.autenticacion.demo.Dto.AdministradorActualizarDTO;
import com.autenticacion.demo.Dto.AdministradorRegistroDTO;
import com.autenticacion.demo.Dto.AdministradorRespuestaDTO;
import com.autenticacion.demo.Entities.Administrador;
import com.autenticacion.demo.Repositories.AdministradorRepository;
import com.autenticacion.demo.Services.AdministradorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.Date;

@Service
public class AdministradorServiceImpl implements AdministradorService {

        Logger logger = LoggerFactory.getLogger(AdministradorService.class);

        @Autowired
        private AdministradorRepository administradorRepository;

        @Override
        public AdministradorRespuestaDTO registrarAdministrador(AdministradorRegistroDTO dto) {
                try {
                        // 1. Crear Administrador en Firebase
                        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                                        .setEmail(dto.getEmail())
                                        .setPassword(dto.getPassword());

                        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);

                        // 2. Guardar en PostgreSQL
                        Administrador administrador = Administrador.builder()
                                        .nombre(dto.getNombre())
                                        .email(dto.getEmail())
                                        .password(dto.getPassword()) // puedes encriptarlo si quieres
                                        .fechaRegistro(new Date())
                                        .estadoCuenta("Activo")
                                        .build();

                        Administrador guardado = administradorRepository.save(administrador);

                        // 3. Retornar DTO
                        return AdministradorRespuestaDTO.builder()
                                        .id(guardado.getId())
                                        .nombre(guardado.getNombre())
                                        .email(guardado.getEmail())
                                        .estadoCuenta(guardado.getEstadoCuenta())
                                        .build();

                } catch (Exception e) {
                        throw new RuntimeException("Error al registrar el administrador: " + e.getMessage());
                }
        }

        @Override
        public AdministradorRespuestaDTO obtenerAdministradorPorEmail(String email) {
                Administrador administrador = administradorRepository.findByEmail(email)
                                .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));

                return AdministradorRespuestaDTO.builder()
                                .id(administrador.getId())
                                .nombre(administrador.getNombre())
                                .email(administrador.getEmail())
                                .estadoCuenta(administrador.getEstadoCuenta())
                                .build();
        }

        @Transactional
        @Override
        public boolean actualizarAdministrador(Long id, AdministradorActualizarDTO dto) {

                String nombre = dto.getNombre();
                String email = dto.getEmail();

                int camposActualizados = administradorRepository.actualizarAdministrador(id, nombre, email);

                return camposActualizados > 0;
        }

        @Override
        public void eliminarAdministrador(Long id) {
                Administrador administrador = administradorRepository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Administrador con id " + id + " no encontrado"));

                administradorRepository.delete(administrador);
        }
}