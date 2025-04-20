package com.autenticacion.demo.Services;

import com.autenticacion.demo.Dto.AdministradorActualizarDTO;
import com.autenticacion.demo.Dto.AdministradorRegistroDTO;
import com.autenticacion.demo.Dto.AdministradorRespuestaDTO;
import com.autenticacion.demo.Dto.CambioPasswordDTO;

public interface AdministradorService {

    AdministradorRespuestaDTO registrarAdministrador(AdministradorRegistroDTO dto);

    Long obtenerIdAdministradorPorEmail(String email);

    boolean actualizarAdministrador(Long id, AdministradorActualizarDTO dto);

    void eliminarAdministrador(Long id);

    void cambiarPassword(CambioPasswordDTO dto);

    AdministradorRespuestaDTO obtenerAdministradorPorId(Long id);
}
