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

    @Enumerated(EnumType.STRING)
    private StatusProposta status = StatusProposta.EM_ANALISE;

    @OneToOne(mappedBy = "proposta", cascade = CascadeType.ALL)
    private Cartao cartao;

    @Deprecated
    public Proposta(){}

    public Proposta(String documento, String email, String nome, String endereco,
                    BigDecimal salario) {
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

    public StatusProposta getStatus() {
        return status;
    }

    public Cartao getCartao() {
        return cartao;
    }

    //seta o status da proposta de acordo com o resultado da solicitação
    public void atualizaStatus(StatusProposta status) {
        this.status = status;
    }

    public void associaCartao(Cartao cartao) {
        this.cartao = cartao;
    }
}
