package br.com.zupacademy.brenonoccioli.proposta.controller.rotinas;

import br.com.zupacademy.brenonoccioli.proposta.controller.clients.CartaoClient;
import br.com.zupacademy.brenonoccioli.proposta.controller.dto.CartaoDto;
import br.com.zupacademy.brenonoccioli.proposta.model.Cartao;
import br.com.zupacademy.brenonoccioli.proposta.model.Proposta;
import br.com.zupacademy.brenonoccioli.proposta.model.StatusProposta;
import br.com.zupacademy.brenonoccioli.proposta.repository.PropostaRepository;
import br.com.zupacademy.brenonoccioli.proposta.utils.ExecutaTransacao;
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
    CartaoClient client;
    @Autowired
    ExecutaTransacao executaTransacao;

    Logger log = LoggerFactory.getLogger(RotinaVinculaCartao.class);

    @Scheduled(fixedDelay = 30000)
    public void vinculaCartao(){
        log.debug("Início da rotina de vinculação de cartões");

        List<Proposta> propostasElegiveisSemCartao = propostaRepository
                .findAllByCartaoIsNullAndStatusIs(StatusProposta.ELEGIVEL);

        propostasElegiveisSemCartao.forEach(proposta ->{

            try{
                CartaoDto cartaoDto = client.obtemCartao(proposta.getId().toString());
                Cartao cartao = cartaoDto.toModel(proposta);
                proposta.associaCartao(cartao);
                proposta.atualizaStatus(StatusProposta.CARTAO_VINCULADO);
                executaTransacao.atualizaEComita(proposta);

                log.debug("Vinculacao concluida com sucesso - proposta: " + proposta.getId());
            } catch (FeignException e){
                log.error("Falha na vinculação do cartão à proposta " + proposta.getId());
            }
        });
        log.info("Final da rotina de vinculação de cartões");
    }
}
