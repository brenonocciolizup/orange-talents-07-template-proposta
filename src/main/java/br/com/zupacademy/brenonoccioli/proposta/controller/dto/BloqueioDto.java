package br.com.zupacademy.brenonoccioli.proposta.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BloqueioDto {

    private String resultado;

    public BloqueioDto(@JsonProperty("resultado") String resultado){
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }
}
