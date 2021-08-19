package br.com.zupacademy.brenonoccioli.proposta.utils;

import br.com.zupacademy.brenonoccioli.proposta.controller.ConsultaCartaoClient;
import br.com.zupacademy.brenonoccioli.proposta.controller.dto.CartaoDto;
import br.com.zupacademy.brenonoccioli.proposta.model.Cartao;
import br.com.zupacademy.brenonoccioli.proposta.model.Proposta;
import br.com.zupacademy.brenonoccioli.proposta.model.StatusProposta;
import br.com.zupacademy.brenonoccioli.proposta.repository.PropostaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RotinaVinculaCartao {
    @Autowired
    PropostaRepository propostaRepository;
    @Autowired
    ConsultaCartaoClient client;
    @Autowired
    ExecutaTransacao executaTransacao;

    Logger log = LoggerFactory.getLogger(RotinaVinculaCartao.class);

    @Scheduled(fixedDelay = 60000)
    public void vinculaCartao(){
        log.info("Inicio da rotina de vinculação de cartões");

        List<Proposta> propostasElegiveisSemCartao = propostaRepository
                .findAllByCartaoIsNullAndStatusIs(StatusProposta.ELEGIVEL);

        propostasElegiveisSemCartao.forEach(proposta ->{

            try{
                CartaoDto cartaoDto = client.obtemCartao(proposta.getId().toString());
                Cartao cartao = cartaoDto.toModel(proposta);
                proposta.associaCartao(cartao);
                executaTransacao.atualizaEComita(proposta);

                log.info("Vinculacao concluida com sucesso - proposta: " + proposta.getId());
            } catch (FeignException e){
                log.error("Falha na vinculação do cartão à proposta " + proposta.getId());
            }
        });
    }
}
