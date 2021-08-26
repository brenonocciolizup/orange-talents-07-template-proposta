package br.com.zupacademy.brenonoccioli.proposta.model;

import br.com.zupacademy.brenonoccioli.proposta.controller.form.AvisoViagemForm;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AvisoViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String destino;

    @NotNull
    @Future
    private LocalDate validoAte;

    @NotNull
    private LocalDateTime avisadoEm = LocalDateTime.now();

    @NotBlank
    private String enderecoIP;

    @NotBlank
    private String userAgent;

    @NotNull
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public AvisoViagem(){}

    public AvisoViagem(AvisoViagemForm aviso, String xForwardedFor, String userAgent, Cartao cartao) {
        this.destino = aviso.getDestino();
        this.validoAte = aviso.getValidoAte();
        this.enderecoIP = xForwardedFor;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public LocalDateTime getAvisadoEm() {
        return avisadoEm;
    }

    public String getEnderecoIP() {
        return enderecoIP;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
