package com.autenticacion.demo.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ErrorsDTO {
  private String errorCode;
  private String path;
  private String message;
  private String url;

  public ErrorsDTO(String path, String errorMessage) {
    this.path = path;
    this.message = errorMessage;
  }
}