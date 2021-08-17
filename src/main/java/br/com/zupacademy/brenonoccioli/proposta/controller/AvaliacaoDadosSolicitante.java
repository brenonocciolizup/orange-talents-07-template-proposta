package br.com.zupacademy.brenonoccioli.proposta.controller;

import br.com.zupacademy.brenonoccioli.proposta.controller.dto.AnaliseDto;
import br.com.zupacademy.brenonoccioli.proposta.controller.dto.SolicitacaoAnaliseForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "avaliaClientes", url = "http://localhost:9999")
public interface AvaliacaoDadosSolicitante {
    @PostMapping("/api/solicitacao")
    AnaliseDto avalia(@RequestBody @Valid SolicitacaoAnaliseForm form);
}
