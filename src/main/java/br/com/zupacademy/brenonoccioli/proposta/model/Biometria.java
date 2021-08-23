package br.com.zupacademy.brenonoccioli.proposta.model;

import br.com.zupacademy.brenonoccioli.proposta.utils.FingerPrintEncoded;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String fingerPrint;

    private LocalDateTime criadoEm = LocalDateTime.now();

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Biometria(){}

    public Biometria(FingerPrintEncoded fingerPrint, Cartao cartao){
        this.fingerPrint = fingerPrint.get();
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }
}
