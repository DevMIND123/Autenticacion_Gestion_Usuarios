package com.autenticacion.demo.Services.impl;

import com.autenticacion.demo.Dto.*;
import com.autenticacion.demo.Entities.Cliente;
import com.autenticacion.demo.Entities.Rol;
import com.autenticacion.demo.Repositories.AdministradorRepository;
import com.autenticacion.demo.Repositories.ClienteRepository;
import com.autenticacion.demo.Repositories.EmpresaRepository;
import com.autenticacion.demo.Services.ClienteService;
import com.autenticacion.demo.Services.KafkaProducerService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private KafkaProducerService kafkaProducer;
    
    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private AdministradorRepository administradorRepository ;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public ClienteRespuestaDTO registrarCliente(ClienteRegistroDTO dto) {
        Cliente cliente = Cliente.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nombre(dto.getNombre())
                .estadoCuenta("Activo")
                .fechaRegistro(new Date())
                .rol(Rol.CLIENTE)
                .build();

        Cliente guardado = clienteRepository.save(cliente);

        // 🔁 ENVIAR EVENTO A KAFKA
        String mensaje = String.format("{\"idUsuario\": %d, \"nombre\": \"%s\"}",
                guardado.getId(), guardado.getNombre());
        kafkaProducer.enviarMensaje(mensaje);

        return ClienteRespuestaDTO.builder()
                .id(guardado.getId())
                .nombre(guardado.getNombre())
                .email(guardado.getEmail())
                .estadoCuenta(guardado.getEstadoCuenta())
                .rol(dto.getRol())
                .build();
    }
    
    @Override
    public Long obtenerIdClientePorEmail(String email) {
        return clienteRepository.findByEmail(email)
                .map(Cliente::getId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    @Override
    @Transactional
    public boolean actualizarCliente(Long id, ClienteActualizarDTO dto) {
        return clienteRepository.actualizarCliente(id, dto.getEmail()) > 0;
    }

    @Override
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public void cambiarPassword(CambioPasswordDTO dto) {
        Cliente cliente = clienteRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        cliente.setPassword(passwordEncoder.encode(dto.getNuevaPassword()));
        clienteRepository.save(cliente);
    }

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                return clienteRepository.findByEmail(email)
                        .map(user -> (UserDetails) user)
                        .or(() -> empresaRepository.findByEmail(email).map(user -> (UserDetails) user))
                        .or(() -> administradorRepository.findByEmail(email).map(user -> (UserDetails) user))
                        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
            }
        };
    }
}