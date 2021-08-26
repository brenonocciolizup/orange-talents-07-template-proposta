package br.com.zupacademy.brenonoccioli.proposta.controller.form;

import br.com.zupacademy.brenonoccioli.proposta.model.AvisoViagem;
import br.com.zupacademy.brenonoccioli.proposta.model.Cartao;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.swing.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemForm {
    @NotBlank
    private String destino;

    @NotNull
    @Future
    private LocalDate terminaEm;

    public AvisoViagemForm(String destino, LocalDate terminaEm) {
        this.destino = destino;
        this.terminaEm = terminaEm;
    }

    public AvisoViagem toModel(String xForwardedFor, String userAgent, Cartao cartao){
        return new AvisoViagem(this, xForwardedFor, userAgent, cartao);
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getTerminaEm() {
        return terminaEm;
    }
}
