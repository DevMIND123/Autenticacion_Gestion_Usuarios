package com.autenticacion.demo.Services;

import com.autenticacion.demo.Dto.UsuarioRegistroDTO;
import com.autenticacion.demo.Dto.UsuarioRespuestaDTO;

public interface UsuarioService {
    UsuarioRespuestaDTO registrarUsuario(UsuarioRegistroDTO dto);
    UsuarioRespuestaDTO obtenerUsuarioPorEmail(String email);
}
