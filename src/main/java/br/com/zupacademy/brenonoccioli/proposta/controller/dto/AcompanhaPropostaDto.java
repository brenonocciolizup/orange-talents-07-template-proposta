package br.com.zupacademy.brenonoccioli.proposta.controller.dto;

import br.com.zupacademy.brenonoccioli.proposta.model.StatusProposta;

public class AcompanhaPropostaDto {
    private StatusProposta status;

    private String documento;

    public AcompanhaPropostaDto(StatusProposta status, String documento){
        this.status = status;
        this.documento = documento;
    }

    public StatusProposta getStatus() {
        return status;
    }

    public String getDocumento() {
        return documento;
    }
}
