package com.autenticacion.demo.Services;

import com.autenticacion.demo.Dto.UsuarioActualizarDTO;
import com.autenticacion.demo.Dto.UsuarioRegistroDTO;
import com.autenticacion.demo.Dto.UsuarioRespuestaDTO;

public interface UsuarioService {
    UsuarioRespuestaDTO registrarUsuario(UsuarioRegistroDTO dto);

    UsuarioRespuestaDTO obtenerUsuarioPorEmail(String email);

    boolean actualizarUsuario(Long id, UsuarioActualizarDTO usuario);

    void eliminarUsuario(Long id);
}
