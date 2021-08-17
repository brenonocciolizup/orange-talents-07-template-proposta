package br.com.zupacademy.brenonoccioli.proposta.model;

import br.com.zupacademy.brenonoccioli.proposta.controller.dto.ResultadoSolicitacao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String documento;

    @NotBlank
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Column(nullable = false)
    private String endereco;

    @NotNull
    @Column(nullable = false)
    private BigDecimal salario;

    @Enumerated
    private StatusProposta status;

    @Deprecated
    public Proposta(){}

    public Proposta(String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    //seta o status da proposta de acordo com o retorno resultado da solicitação
    public void atualizaStatus(ResultadoSolicitacao resultado) {
        this.status = resultado.getStatusProposta();
    }
}
