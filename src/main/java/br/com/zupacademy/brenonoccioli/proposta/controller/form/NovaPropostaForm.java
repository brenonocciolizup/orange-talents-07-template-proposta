package br.com.zupacademy.brenonoccioli.proposta.controller.form;

import br.com.zupacademy.brenonoccioli.proposta.model.Proposta;
import br.com.zupacademy.brenonoccioli.proposta.repository.PropostaRepository;
import br.com.zupacademy.brenonoccioli.proposta.utils.Criptografia;
import br.com.zupacademy.brenonoccioli.proposta.validacao.annotations.DocumentoValido;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class NovaPropostaForm {

    @NotBlank
    @DocumentoValido
    private String documento;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotNull
    @Positive
    private BigDecimal salario;

    public NovaPropostaForm(String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Proposta toModel() {
        return new Proposta(documento, email, nome, endereco, salario);
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }


    public boolean documentoJaExiste(PropostaRepository repository, byte[] documentoHash){
        return repository.existsByDocumentoHash(documentoHash);
    }
}
