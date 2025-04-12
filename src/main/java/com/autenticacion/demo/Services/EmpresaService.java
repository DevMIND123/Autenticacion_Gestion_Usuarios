package com.autenticacion.demo.Services;

import com.autenticacion.demo.Dto.CambioPasswordDTO;
import com.autenticacion.demo.Dto.EmpresaActualizarDTO;
import com.autenticacion.demo.Dto.EmpresaRegistroDTO;
import com.autenticacion.demo.Dto.EmpresaRespuestaDTO;

public interface EmpresaService {

    EmpresaRespuestaDTO registrarEmpresa(EmpresaRegistroDTO dto);

    EmpresaRespuestaDTO obtenerEmpresaPorEmail(String email);

    void eliminarEmpresa(Long id);

    void cambiarPassword(CambioPasswordDTO dto);

    void actualizarEmpresa(Long id, EmpresaActualizarDTO dto);
}
