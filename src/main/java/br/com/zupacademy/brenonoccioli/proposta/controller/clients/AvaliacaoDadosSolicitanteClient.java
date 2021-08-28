package br.com.zupacademy.brenonoccioli.proposta.controller.clients;

import br.com.zupacademy.brenonoccioli.proposta.controller.dto.AnaliseDto;
import br.com.zupacademy.brenonoccioli.proposta.controller.form.SolicitacaoAnaliseForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "avaliaClientes", url = "${avaliacao.client.url}")
public interface AvaliacaoDadosSolicitanteClient {
    @PostMapping("/solicitacao")
    AnaliseDto avalia(@RequestBody @Valid SolicitacaoAnaliseForm form);
}
