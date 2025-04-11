package com.autenticacion.demo.Services;

import com.autenticacion.demo.Dto.AdministradorActualizarDTO;
import com.autenticacion.demo.Dto.AdministradorRegistroDTO;
import com.autenticacion.demo.Dto.AdministradorRespuestaDTO;

public interface AdministradorService {

    AdministradorRespuestaDTO registrarAdministrador(AdministradorRegistroDTO dto);

    AdministradorRespuestaDTO obtenerAdministradorPorEmail(String email);

    boolean actualizarAdministrador(Long id, AdministradorActualizarDTO usuario);

    void eliminarAdministrador(Long id);
}
