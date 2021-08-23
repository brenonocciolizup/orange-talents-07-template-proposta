package br.com.zupacademy.brenonoccioli.proposta.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numeroCartao;

    private LocalDateTime emitidoEm;

    private String titular;

    private Integer limite;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Vencimento vencimento;

    @OneToOne
    @NotNull
    @JoinColumn(name = "idProposta")
    private Proposta proposta;

    @OneToMany(mappedBy = "cartao", cascade = {CascadeType.ALL})
    private List<Biometria> biometrias = new ArrayList<>();

    @Deprecated
    public Cartao(){}

    public Cartao(String id, LocalDateTime emitidoEm, String titular,
                  Integer limite, Vencimento vencimento, Proposta proposta) {
        this.numeroCartao = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.vencimento = vencimento;
        this.proposta = proposta;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public Integer getLimite() {
        return limite;
    }

    public Vencimento getVencimento() {
        return vencimento;
    }

    public Proposta getProposta() {
        return proposta;
    }

    public List<Biometria> getBiometrias() {
        return biometrias;
    }
}


