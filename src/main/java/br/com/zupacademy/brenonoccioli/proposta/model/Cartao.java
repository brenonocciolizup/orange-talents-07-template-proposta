package br.com.zupacademy.brenonoccioli.proposta.model;

import br.com.zupacademy.brenonoccioli.proposta.controller.dto.BloqueioDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;

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

    @Enumerated
    private StatusCartao status;

    @OneToMany(mappedBy = "cartao", cascade = {CascadeType.ALL})
    private List<BloqueioCartao> bloqueios = new ArrayList<>();

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private List<AvisoViagem> avisosViagem = new ArrayList<>();

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private Set<Carteira> carteiras = new HashSet<>();

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
        this.status = StatusCartao.ATIVO;
    }

    public Long getId() {
        return id;
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

    public List<AvisoViagem> getAvisosViagem() {
        return avisosViagem;
    }

    public Set<Carteira> getCarteiras() {
        return carteiras;
    }

    public boolean estaBloqueado() {
        if(status == StatusCartao.BLOQUEADO || status == StatusCartao.BLOQUEIO_SOLICITADO){
            return true;
        }
        return false;
    }

    public void solicitaBloqueio(String xForwardedFor, String userAgent) {
        BloqueioCartao bloqueio = new BloqueioCartao(xForwardedFor, userAgent, this);
        this.bloqueios.add(bloqueio);
        this.status = StatusCartao.BLOQUEIO_SOLICITADO;
    }

    public void bloqueia(){
        this.status = StatusCartao.BLOQUEADO;
    }

    public boolean carteiraJaAssociada(TipoCarteira carteira){
        if(this.carteiras.contains(carteira)) return true;
        return false;
    }
}


