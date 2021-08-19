package br.com.zupacademy.brenonoccioli.proposta.controller.dto;

import br.com.zupacademy.brenonoccioli.proposta.model.Vencimento;

import java.time.LocalDateTime;

public class VencimentoDto {
    private String id;
    private Integer dia;
    private LocalDateTime dataDeCriacao;

    public VencimentoDto(String id, Integer dia, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataDeCriacao = dataDeCriacao;
    }

    public Vencimento toModel() {
        return new Vencimento(id, dia, dataDeCriacao);
    }
}
