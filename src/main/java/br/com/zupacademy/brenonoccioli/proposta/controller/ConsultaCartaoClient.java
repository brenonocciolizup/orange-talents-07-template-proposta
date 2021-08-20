package br.com.zupacademy.brenonoccioli.proposta.controller;

import br.com.zupacademy.brenonoccioli.proposta.controller.dto.CartaoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="consultaCartao", url="${cartoes.client.url}")
public interface ConsultaCartaoClient {
    @GetMapping("/api/cartoes")
    CartaoDto obtemCartao(@RequestParam String idProposta);
}
