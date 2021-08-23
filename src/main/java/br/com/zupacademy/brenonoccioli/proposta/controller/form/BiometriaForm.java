package br.com.zupacademy.brenonoccioli.proposta.controller.form;

import br.com.zupacademy.brenonoccioli.proposta.model.Biometria;
import br.com.zupacademy.brenonoccioli.proposta.model.Cartao;
import br.com.zupacademy.brenonoccioli.proposta.utils.FingerPrintEncoded;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class BiometriaForm {

    @NotBlank
    private String fingerPrint;

    public BiometriaForm(@JsonProperty(value = "fingerPrint") String fingerPrint){
        this.fingerPrint = fingerPrint;
    }

    public Biometria toModel(Cartao cartao){
        return new Biometria(new FingerPrintEncoded(fingerPrint), cartao);
    }

    public String getFingerPrint() {
        return fingerPrint;
    }
}
