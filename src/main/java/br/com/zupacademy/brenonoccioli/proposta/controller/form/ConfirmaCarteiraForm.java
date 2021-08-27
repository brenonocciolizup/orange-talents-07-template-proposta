package br.com.zupacademy.brenonoccioli.proposta.controller.form;

import br.com.zupacademy.brenonoccioli.proposta.model.TipoCarteira;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ConfirmaCarteiraForm {

    @Email
    @NotBlank
    private String email;

    @NotNull
    private TipoCarteira carteira;

    public ConfirmaCarteiraForm(CarteiraForm form){
        this.email = form.getEmail();
        this.carteira = form.getCarteira();
    }

    public String getEmail() {
        return email;
    }

    public TipoCarteira getCarteira() {
        return carteira;
    }
}
