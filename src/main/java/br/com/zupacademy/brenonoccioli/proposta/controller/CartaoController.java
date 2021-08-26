package br.com.zupacademy.brenonoccioli.proposta.controller;

import br.com.zupacademy.brenonoccioli.proposta.controller.dto.AvisoDto;
import br.com.zupacademy.brenonoccioli.proposta.controller.form.AvisoViagemForm;
import br.com.zupacademy.brenonoccioli.proposta.controller.form.BiometriaForm;
import br.com.zupacademy.brenonoccioli.proposta.controller.form.ConfirmaAvisoForm;
import br.com.zupacademy.brenonoccioli.proposta.model.AvisoViagem;
import br.com.zupacademy.brenonoccioli.proposta.model.Biometria;
import br.com.zupacademy.brenonoccioli.proposta.model.Cartao;
import br.com.zupacademy.brenonoccioli.proposta.repository.CartaoRepository;
import br.com.zupacademy.brenonoccioli.proposta.utils.ExecutaTransacao;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;


@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    CartaoRepository cartaoRepository;
    @Autowired
    ExecutaTransacao executaTransacao;
    @Autowired
    CartaoClient client;


    @PostMapping("/{idCartao}/biometrias")
    public ResponseEntity criaBiometria(@PathVariable Long idCartao,
                                        @RequestBody @Valid BiometriaForm form,
                                        UriComponentsBuilder builder){

        Optional<Cartao> cartaoOptional = cartaoRepository.findById(idCartao);
        if(cartaoOptional.isEmpty()){
            return ResponseEntity.status(404).build();
        }

        Cartao cartao = cartaoOptional.get();
        Biometria biometria = form.toModel(cartao);
        executaTransacao.salvaEComita(biometria);

        URI uri = builder.path("/biometrias/{id}").buildAndExpand(biometria.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/{id}/bloqueio")
    public ResponseEntity bloqueioDeCartao(@PathVariable("id") Long id,
                                 @RequestHeader("X-Forwarded-For") String xForwardedFor,
                                 @RequestHeader("User-Agent") String userAgent){

        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        if(cartaoOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = cartaoOptional.get();

        if(cartao.estaBloqueado()){
            return ResponseEntity.unprocessableEntity().body("cartão já está bloquado");
        }

        cartao.solicitaBloqueio(xForwardedFor, userAgent);
        executaTransacao.atualizaEComita(cartao);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/avisos")
    public ResponseEntity avisaViagem(@PathVariable("id") Long id,
                            @RequestHeader("X-Forwarded-For") String xForwardedFor,
                            @RequestHeader("User-Agent") String userAgent,
                            @RequestBody @Valid AvisoViagemForm form){

        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        if(cartaoOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = cartaoOptional.get();

        try{
            ConfirmaAvisoForm confirmaForm = new ConfirmaAvisoForm(form);
            client.confirmaAvisoViagem(cartao.getNumeroCartao(), confirmaForm);
        } catch (FeignException e){
            return ResponseEntity.status(e.status()).body("solicitação não processada");
        }

        AvisoViagem aviso = form.toModel(xForwardedFor, userAgent, cartao);
        executaTransacao.salvaEComita(aviso);
        return ResponseEntity.ok().build();
    }
}
