package br.com.zupacademy.brenonoccioli.proposta.controller.form;

import br.com.zupacademy.brenonoccioli.proposta.model.Proposta;

public class SolicitacaoAnaliseForm {
    private String documento;
    private String nome;
    private String idProposta;

    public SolicitacaoAnaliseForm(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId().toString();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
