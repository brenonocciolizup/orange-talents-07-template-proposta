package br.com.zupacademy.brenonoccioli.proposta.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AvisoDto {
    private String resultado;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AvisoDto( String resultado){
        this.resultado = resultado;
    }
}
