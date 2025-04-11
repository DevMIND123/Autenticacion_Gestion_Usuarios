package com.autenticacion.demo.Exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import com.autenticacion.demo.Dto.ErrorsDTO;

@Getter
@NoArgsConstructor
public class StandarizedApiExceptionResponse {

    private int code;
    private long id;
    private String message;
    private List<ErrorsDTO> errors;

    public StandarizedApiExceptionResponse(int code, long id, String message) {
        super();
        this.code = code;
        this.id = id;
        this.message = message;
    }

    public StandarizedApiExceptionResponse(int code, long id, String message, List<ErrorsDTO> errors) {
        super();
        this.code = code;
        this.id = id;
        this.message = message;
        this.errors = errors;
    }
}
