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
import java.util.List;
import java.util.Optional;

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
        String mensaje = String.format("{\"id\": %d, \"nombre\": \"%s\", \"tipo\": \"%s\"}",
        guardado.getId(), guardado.getNombre(), guardado.getRol().name());
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
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);

        if (optionalCliente.isEmpty()) {
            return false;
        }

        Cliente cliente = optionalCliente.get();
        cliente.setNombre(dto.getNombre());
        cliente.setEmail(dto.getEmail());

        clienteRepository.save(cliente);
        return true;
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

    @Override
    public ClienteRespuestaDTO obtenerClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
        return ClienteRespuestaDTO.builder()
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .email(cliente.getEmail())
                .estadoCuenta(cliente.getEstadoCuenta())
                .rol(cliente.getRol())
                .build();
    }
  
    @Override
    public List<ClienteRespuestaDTO> obtenerTodosLosClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(cliente -> ClienteRespuestaDTO.builder()
                        .id(cliente.getId())
                        .nombre(cliente.getNombre())
                        .email(cliente.getEmail())
                        .estadoCuenta(cliente.getEstadoCuenta())
                        .rol(cliente.getRol())
                        .build())
                .toList();
    }
}