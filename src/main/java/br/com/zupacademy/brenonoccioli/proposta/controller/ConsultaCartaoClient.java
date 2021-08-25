package br.com.zupacademy.brenonoccioli.proposta.controller;

import br.com.zupacademy.brenonoccioli.proposta.controller.dto.BloqueioDto;
import br.com.zupacademy.brenonoccioli.proposta.controller.dto.CartaoDto;
import br.com.zupacademy.brenonoccioli.proposta.controller.form.BloqueioForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="consultaCartao", url="${cartoes.client.url}")
public interface ConsultaCartaoClient {
    @GetMapping("/cartoes")
    CartaoDto obtemCartao(@RequestParam String idProposta);

    @PostMapping("/cartoes/{id}/bloqueios")
    BloqueioDto solicitaBloqueioCartao(@PathVariable String id, @RequestBody BloqueioForm form);
}
