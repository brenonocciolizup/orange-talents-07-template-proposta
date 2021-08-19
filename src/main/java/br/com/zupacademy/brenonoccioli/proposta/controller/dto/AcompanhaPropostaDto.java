package br.com.zupacademy.brenonoccioli.proposta.controller.dto;

import br.com.zupacademy.brenonoccioli.proposta.model.StatusProposta;

public class AcompanhaPropostaDto {
    private StatusProposta status;

    public AcompanhaPropostaDto(StatusProposta status){
        this.status = status;
    }

    public StatusProposta getStatus() {
        return status;
    }
}
