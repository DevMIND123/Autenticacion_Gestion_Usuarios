package com.autenticacion.demo.Services.impl;

import com.autenticacion.demo.Dto.AdministradorActualizarDTO;
import com.autenticacion.demo.Dto.AdministradorRegistroDTO;
import com.autenticacion.demo.Dto.AdministradorRespuestaDTO;
import com.autenticacion.demo.Dto.CambioPasswordDTO;
import com.autenticacion.demo.Entities.Administrador;
import com.autenticacion.demo.Repositories.AdministradorRepository;
import com.autenticacion.demo.Services.AdministradorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.Date;

@Service
public class AdministradorServiceImpl implements AdministradorService {

        Logger logger = LoggerFactory.getLogger(AdministradorServiceImpl.class);

        @Autowired
        private AdministradorRepository administradorRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Override
public AdministradorRespuestaDTO registrarAdministrador(AdministradorRegistroDTO dto) {
    try {
        logger.info("📥 Iniciando registro de administrador...");
        logger.info("📨 DTO recibido - nombre: {}, email: {}, rol: {}", 
                    dto.getNombre(), dto.getEmail(), dto.getRol());

        // 1. Crear Administrador en Firebase
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(dto.getEmail())
                .setPassword(dto.getPassword());

        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
        logger.info("✅ Usuario creado en Firebase con UID: {}", userRecord.getUid());

        // 2. Guardar en PostgreSQL
        Administrador administrador = Administrador.builder()
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .estadoCuenta("Activo")
                .rol(dto.getRol())
                .build();

        logger.info("🧱 Objeto Administrador construido: {}", administrador);

        Administrador guardado = administradorRepository.save(administrador);
        logger.info("💾 Administrador guardado con ID: {} y rol: {}", guardado.getId(), guardado.getRol());

        // 3. Retornar DTO
        return AdministradorRespuestaDTO.builder()
                .id(guardado.getId())
                .nombre(guardado.getNombre())
                .email(guardado.getEmail())
                .estadoCuenta(guardado.getEstadoCuenta())
                .rol(guardado.getRol()) // mejor usar el rol que quedó guardado
                .build();

    } catch (Exception e) {
        logger.error("❌ Error al registrar el administrador: {}", e.getMessage());
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

        @Override
        public void cambiarPassword(CambioPasswordDTO dto) {
            try {
                logger.info("🔐 Iniciando cambio de contraseña para admin: {}", dto.getEmail());
    
                // 1. Buscar en Firebase
                UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(dto.getEmail());
                logger.info("✅ Usuario Firebase encontrado: UID={}", userRecord.getUid());
    
                // 2. Actualizar la contraseña en Firebase
                UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(userRecord.getUid())
                        .setPassword(dto.getNuevaPassword());
                FirebaseAuth.getInstance().updateUser(request);
                logger.info("🔁 Contraseña actualizada en Firebase correctamente");
    
                // 3. Buscar administrador en PostgreSQL
                Administrador admin = administradorRepository.findByEmail(dto.getEmail())
                        .orElseThrow(() -> {
                            logger.error("❌ No se encontró el administrador con email {}", dto.getEmail());
                            return new RuntimeException("Administrador no encontrado");
                        });
    
                logger.info("✅ Admin encontrado en BD: id={}, nombre={}", admin.getId(), admin.getNombre());
    
                // 4. Encriptar y guardar en PostgreSQL
                admin.setPassword(passwordEncoder.encode(dto.getNuevaPassword()));
                administradorRepository.save(admin);
                logger.info("✅ Contraseña actualizada y guardada en PostgreSQL");
    
            } catch (Exception e) {
                logger.error("❌ Error al cambiar contraseña del administrador: {}", e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("❌ Error al cambiar contraseña: " + e.getMessage());
            }
        }
    }