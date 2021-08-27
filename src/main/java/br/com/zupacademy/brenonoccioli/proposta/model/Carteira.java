package br.com.zupacademy.brenonoccioli.proposta.model;

import br.com.zupacademy.brenonoccioli.proposta.controller.form.CarteiraForm;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoCarteira tipoCarteira;

    @NotNull
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Carteira(){}

    public Carteira(CarteiraForm form, Cartao cartao) {
        this.tipoCarteira = form.getCarteira();
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }
}
