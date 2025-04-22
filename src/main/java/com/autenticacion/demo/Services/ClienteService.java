package com.autenticacion.demo.Services;

import com.autenticacion.demo.Dto.ClienteActualizarDTO;
import com.autenticacion.demo.Dto.ClienteRegistroDTO;
import com.autenticacion.demo.Dto.ClienteRespuestaDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.autenticacion.demo.Dto.CambioPasswordDTO;

public interface ClienteService {

    ClienteRespuestaDTO registrarCliente(ClienteRegistroDTO dto);

    Long obtenerIdClientePorEmail(String email);

    boolean actualizarCliente(Long id, ClienteActualizarDTO dto);

    void eliminarCliente(Long id);

    void cambiarPassword(CambioPasswordDTO dto);
  
    UserDetailsService userDetailsService();

    ClienteRespuestaDTO obtenerClientePorId(Long id);
}
