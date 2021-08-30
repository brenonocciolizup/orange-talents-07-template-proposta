package br.com.zupacademy.brenonoccioli.proposta.model;

import br.com.zupacademy.brenonoccioli.proposta.controller.dto.ResultadoSolicitacao;
import br.com.zupacademy.brenonoccioli.proposta.utils.Criptografia;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String documento;

    @NotNull
    @Column(nullable = false)
    private byte[] documentoHash;

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
        Criptografia criptografia = new Criptografia();
        this.documento = criptografia.criptografa(documento);
        this.documentoHash = criptografia.gerarHash(documento);
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
