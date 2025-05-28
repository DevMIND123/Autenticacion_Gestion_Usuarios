package com.autenticacion.demo.Services;

import com.autenticacion.demo.Dto.CambioPasswordDTO;
import com.autenticacion.demo.Dto.EmpresaActualizarDTO;
import com.autenticacion.demo.Dto.EmpresaRegistroDTO;
import com.autenticacion.demo.Dto.EmpresaRespuestaDTO;

import java.util.List;

public interface EmpresaService {

    EmpresaRespuestaDTO registrarEmpresa(EmpresaRegistroDTO dto);

    Long obtenerIdEmpresaPorEmail(String email);

    void eliminarEmpresa(Long id);

    void cambiarPassword(CambioPasswordDTO dto);

    void actualizarEmpresa(Long id, EmpresaActualizarDTO dto);

    EmpresaRespuestaDTO obtenerEmpresaPorId(Long id);

    List<EmpresaRespuestaDTO> obtenerEmpresas();
}
