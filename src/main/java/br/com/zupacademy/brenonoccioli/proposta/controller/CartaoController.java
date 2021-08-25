package br.com.zupacademy.brenonoccioli.proposta.controller;

import br.com.zupacademy.brenonoccioli.proposta.controller.form.BiometriaForm;
import br.com.zupacademy.brenonoccioli.proposta.model.Biometria;
import br.com.zupacademy.brenonoccioli.proposta.model.Cartao;
import br.com.zupacademy.brenonoccioli.proposta.repository.CartaoRepository;
import br.com.zupacademy.brenonoccioli.proposta.utils.ExecutaTransacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    CartaoRepository cartaoRepository;
    @Autowired
    ExecutaTransacao executaTransacao;


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

        cartao.bloqueia(xForwardedFor, userAgent);
        executaTransacao.atualizaEComita(cartao);

        return ResponseEntity.ok().build();
    }
}
