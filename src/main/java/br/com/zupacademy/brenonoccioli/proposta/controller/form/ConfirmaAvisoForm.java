package br.com.zupacademy.brenonoccioli.proposta.controller.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ConfirmaAvisoForm {
    @NotBlank
    private String destino;

    @NotNull
    private LocalDate validoAte;

    public ConfirmaAvisoForm(AvisoViagemForm form) {
        this.destino = form.getDestino();
        this.validoAte = form.getValidoAte();
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }
}
