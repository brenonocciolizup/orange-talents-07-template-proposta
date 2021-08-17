package br.com.zupacademy.brenonoccioli.proposta.controller;

import br.com.zupacademy.brenonoccioli.proposta.controller.dto.NovaPropostaForm;
import br.com.zupacademy.brenonoccioli.proposta.model.Proposta;
import br.com.zupacademy.brenonoccioli.proposta.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class NovaPropostaController {

    @Autowired
    PropostaRepository propostaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity criaNovaProposta(@RequestBody @Valid NovaPropostaForm form, UriComponentsBuilder builder){

        if(form.documentoJaExiste(propostaRepository)){
            return ResponseEntity.status(422).body("Apenas uma proposta por documento!");
        }

        Proposta proposta = form.toModel();
        propostaRepository.save(proposta);

        URI uri = builder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
