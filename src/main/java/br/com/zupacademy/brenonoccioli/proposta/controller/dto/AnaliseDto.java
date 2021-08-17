package br.com.zupacademy.brenonoccioli.proposta.controller.dto;

public class AnaliseDto {

    private String documento;
    private String nome;
    private ResultadoSolicitacao resultadoSolicitacao;
    private String idProposta;

    public AnaliseDto(String documento, String nome, ResultadoSolicitacao resultadoSolicitacao, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
        this.idProposta = idProposta;
    }

    public ResultadoSolicitacao getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }
}
