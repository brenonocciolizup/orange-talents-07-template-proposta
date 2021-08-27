package br.com.zupacademy.brenonoccioli.proposta.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ResultadoCarteira {

    private String resultado;

    private String id;

    public ResultadoCarteira(String resultado, String id) {
        this.resultado = resultado;
        this.id = id;
    }
}
