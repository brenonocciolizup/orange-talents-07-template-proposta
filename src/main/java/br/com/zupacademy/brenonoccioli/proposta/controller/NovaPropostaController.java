package br.com.zupacademy.brenonoccioli.proposta.controller;

import br.com.zupacademy.brenonoccioli.proposta.config.metricas.MetricasPrometheus;
import br.com.zupacademy.brenonoccioli.proposta.controller.clients.AvaliacaoDadosSolicitanteClient;
import br.com.zupacademy.brenonoccioli.proposta.controller.dto.*;
import br.com.zupacademy.brenonoccioli.proposta.controller.form.NovaPropostaForm;
import br.com.zupacademy.brenonoccioli.proposta.controller.form.SolicitacaoAnaliseForm;
import br.com.zupacademy.brenonoccioli.proposta.model.Proposta;
import br.com.zupacademy.brenonoccioli.proposta.model.StatusProposta;
import br.com.zupacademy.brenonoccioli.proposta.repository.PropostaRepository;
import br.com.zupacademy.brenonoccioli.proposta.utils.Criptografia;
import br.com.zupacademy.brenonoccioli.proposta.utils.ExecutaTransacao;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/propostas")
public class NovaPropostaController {

    @Autowired
    PropostaRepository propostaRepository;
    @Autowired
    AvaliacaoDadosSolicitanteClient client;
    @Autowired
    ExecutaTransacao executor;
    @Autowired
    MetricasPrometheus metricas;
    @Autowired
    Criptografia criptografia;

    @PostMapping
    public ResponseEntity criaNovaProposta(@RequestBody @Valid NovaPropostaForm form,
                                           UriComponentsBuilder builder) {

        Logger log = LoggerFactory.getLogger(NovaPropostaController.class);

        if(form.documentoJaExiste(propostaRepository, criptografia.gerarHash(form.getDocumento()))){
            return ResponseEntity.status(422).body("Apenas uma proposta por documento!");
        }

        Proposta proposta = form.toModel();
        executor.salvaEComita(proposta);

            try{
                SolicitacaoAnaliseForm solicitacaoAnalise = new SolicitacaoAnaliseForm(proposta);
                //faz a requisição ao serviço externo
                AnaliseDto resultado = client.avalia(solicitacaoAnalise);
                proposta.atualizaStatus(resultado.getResultadoSolicitacao().getStatusProposta());
                executor.atualizaEComita(proposta);
            } catch (FeignException e) {
                metricas.contaFalhaCriacaoDeProposta();
                log.error(e.getMessage());
                if (e.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()) {
                    proposta.atualizaStatus(ResultadoSolicitacao.COM_RESTRICAO.getStatusProposta());
                    executor.atualizaEComita(proposta);
                } else {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                            "Erro ao avaliar proposta, tente novamente mais tarde");
                }
            }

        metricas.contadorDePropostas();
        List<Proposta> lista = propostaRepository.findAllByCartaoIsNullAndStatusIs(StatusProposta.ELEGIVEL);

        URI uri = builder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcompanhaPropostaDto> acompanhaProposta(@PathVariable Long id){
        Optional<Proposta> propostaOptional = propostaRepository.findById(id);

        if(propostaOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        AcompanhaPropostaDto dto = new AcompanhaPropostaDto(propostaOptional.get().getStatus());

        return ResponseEntity.ok(dto);
    }
}
