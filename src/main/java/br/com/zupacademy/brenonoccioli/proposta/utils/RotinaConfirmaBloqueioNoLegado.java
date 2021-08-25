package br.com.zupacademy.brenonoccioli.proposta.utils;

import br.com.zupacademy.brenonoccioli.proposta.controller.ConsultaCartaoClient;
import br.com.zupacademy.brenonoccioli.proposta.controller.dto.BloqueioDto;
import br.com.zupacademy.brenonoccioli.proposta.controller.form.BloqueioForm;
import br.com.zupacademy.brenonoccioli.proposta.model.Cartao;
import br.com.zupacademy.brenonoccioli.proposta.model.StatusCartao;
import br.com.zupacademy.brenonoccioli.proposta.repository.CartaoRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class RotinaConfirmaBloqueioNoLegado {
    @Autowired
    ConsultaCartaoClient client;
    @Autowired
    CartaoRepository repository;
    @Autowired
    ExecutaTransacao executaTransacao;

    Logger log = LoggerFactory.getLogger(RotinaVinculaCartao.class);

    @Scheduled(fixedDelay = 50000)
    public void confirmaBloqueioNoLegado(){
        log.info("Inicio da rotina notificação de bloqueio ao sistema legado");

        BloqueioForm form = new BloqueioForm();

        List<Cartao> listaDeSolicitacaoDeBloqueio = repository.findAllByStatus(StatusCartao.BLOQUEIO_SOLICITADO);

        listaDeSolicitacaoDeBloqueio.forEach(cartao ->{
            try{
                BloqueioDto dto = client.solicitaBloqueioCartao(cartao.getNumeroCartao(), form);
                cartao.bloqueia();
                executaTransacao.atualizaEComita(cartao);
                log.info("cartão " + cartao.getId() + " bloqueado no legado com sucesso");
           }catch (FeignException fe){
                log.info("erro ao bloquear cartão " + cartao.getId() + " no legado");
            }
        });

        log.info("Final da rotina de notificação de bloqueio so sistema legado");
    }
}
