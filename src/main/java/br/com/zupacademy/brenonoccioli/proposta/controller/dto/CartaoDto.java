package br.com.zupacademy.brenonoccioli.proposta.controller.dto;

import br.com.zupacademy.brenonoccioli.proposta.model.Cartao;
import br.com.zupacademy.brenonoccioli.proposta.model.Proposta;
import br.com.zupacademy.brenonoccioli.proposta.model.Vencimento;

import java.time.LocalDateTime;

public class CartaoDto {
    private String id;
    private LocalDateTime emitidoEm;
    private String titular;
    private Integer limite;
    private VencimentoDto vencimento;
    private String idProposta;

    public CartaoDto(String id, LocalDateTime emitidoEm, String titular,
                     Integer limite, VencimentoDto vencimento, String idProposta) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.vencimento = vencimento;
        this.idProposta = idProposta;
    }

    public Cartao toModel(Proposta proposta) {
        Vencimento vencimento = this.vencimento.toModel();
        return new Cartao(id, emitidoEm, titular, limite, vencimento, proposta);
    }

    public String getId() {
        return id;
    }
}
