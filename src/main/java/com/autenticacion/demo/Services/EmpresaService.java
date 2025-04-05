package com.autenticacion.demo.Services;

import com.autenticacion.demo.Dto.EmpresaRegistroDTO;
import com.autenticacion.demo.Dto.EmpresaRespuestaDTO;

public interface EmpresaService {
    EmpresaRespuestaDTO registrarEmpresa(EmpresaRegistroDTO dto);
}
