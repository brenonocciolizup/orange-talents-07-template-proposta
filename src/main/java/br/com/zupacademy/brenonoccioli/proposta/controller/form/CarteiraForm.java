package br.com.zupacademy.brenonoccioli.proposta.controller.form;

import br.com.zupacademy.brenonoccioli.proposta.model.Cartao;
import br.com.zupacademy.brenonoccioli.proposta.model.Carteira;
import br.com.zupacademy.brenonoccioli.proposta.model.TipoCarteira;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarteiraForm {

    @Email
    @NotBlank
    private String email;

    @NotNull
    private TipoCarteira carteira;

    public CarteiraForm(String email, TipoCarteira carteira) {
        this.email = email;
        this.carteira = carteira;
    }
    
    public Carteira toModel(Cartao cartao){
        return new Carteira(this, cartao);
    }

    public String getEmail() {
        return email;
    }

    public TipoCarteira getCarteira() {
        return carteira;
    }

}