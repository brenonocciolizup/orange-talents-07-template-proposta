package br.com.zupacademy.brenonoccioli.proposta.controller;

import br.com.zupacademy.brenonoccioli.proposta.controller.dto.AvisoDto;
import br.com.zupacademy.brenonoccioli.proposta.controller.dto.BloqueioDto;
import br.com.zupacademy.brenonoccioli.proposta.controller.dto.CartaoDto;
import br.com.zupacademy.brenonoccioli.proposta.controller.dto.ResultadoCarteira;
import br.com.zupacademy.brenonoccioli.proposta.controller.form.BloqueioForm;
import br.com.zupacademy.brenonoccioli.proposta.controller.form.CarteiraForm;
import br.com.zupacademy.brenonoccioli.proposta.controller.form.ConfirmaAvisoForm;
import br.com.zupacademy.brenonoccioli.proposta.controller.form.ConfirmaCarteiraForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient(name="consultaCartao", url="${cartoes.client.url}")
public interface CartaoClient {
    @GetMapping("/cartoes")
    CartaoDto obtemCartao(@RequestParam String idProposta);

    @PostMapping("/cartoes/{id}/bloqueios")
    BloqueioDto solicitaBloqueioCartao(@PathVariable String id, @RequestBody @Valid BloqueioForm form);

    @PostMapping("/cartoes/{id}/avisos")
    AvisoDto confirmaAvisoViagem(@PathVariable String id, @RequestBody @Valid ConfirmaAvisoForm form);

    @PostMapping("/cartoes/{id}/carteiras")
    ResultadoCarteira confirmaAssociacaoCarteira(@PathVariable String id, @RequestBody @Valid ConfirmaCarteiraForm form);
}
