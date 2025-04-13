package com.autenticacion.demo.security;

import com.autenticacion.demo.Entities.Administrador;
import com.autenticacion.demo.Entities.Empresa;
import com.autenticacion.demo.Entities.Cliente;
import com.autenticacion.demo.Repositories.AdministradorRepository;
import com.autenticacion.demo.Repositories.EmpresaRepository;
import com.autenticacion.demo.Repositories.ClienteRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdministradorRepository administradorRepo;
    private final EmpresaRepository empresaRepo;
    private final ClienteRepository clienteRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Administrador admin = administradorRepo.findByEmail(email).orElse(null);
        if (admin != null) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(admin.getEmail())
                    .password(admin.getPassword())
                    .roles(admin.getRol().name())
                    .build();
        }

        Empresa empresa = empresaRepo.findByEmail(email).orElse(null);
        if (empresa != null) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(empresa.getEmail())
                    .password(empresa.getPassword())
                    .roles("EMPRESA")
                    .build();
        }

        Cliente cliente = clienteRepo.findByEmail(email).orElse(null);
        if (cliente != null) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(cliente.getEmail())
                    .password(cliente.getPassword())
                    .roles("CLIENTE")
                    .build();
        }

        throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
    }
}
