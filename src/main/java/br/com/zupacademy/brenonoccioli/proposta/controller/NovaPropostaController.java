package br.com.zupacademy.brenonoccioli.proposta.controller;

import br.com.zupacademy.brenonoccioli.proposta.controller.dto.*;
import br.com.zupacademy.brenonoccioli.proposta.model.Proposta;
import br.com.zupacademy.brenonoccioli.proposta.model.StatusProposta;
import br.com.zupacademy.brenonoccioli.proposta.repository.PropostaRepository;
import br.com.zupacademy.brenonoccioli.proposta.utils.ExecutaTransacao;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

    @PostMapping
    public ResponseEntity criaNovaProposta(@RequestBody @Valid NovaPropostaForm form, UriComponentsBuilder builder){

        if(form.documentoJaExiste(propostaRepository)){
            return ResponseEntity.status(422).body("Apenas uma proposta por documento!");
        }

        Proposta proposta = form.toModel();
        executor.salvaEComita(proposta);

            try{
                SolicitacaoAnaliseForm solicitacaoAnalise = new SolicitacaoAnaliseForm(proposta);
                //faz a requisição ao serviço externo
                AnaliseDto resultado = client.avalia(solicitacaoAnalise);
                proposta.atualizaStatus(resultado.getResultadoSolicitacao());
                executor.atualizaEComita(proposta);
            } catch (FeignException e) {
                if (e.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()) {
                    proposta.atualizaStatus(ResultadoSolicitacao.COM_RESTRICAO);
                    executor.atualizaEComita(proposta);
                } else {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                            "Erro durante ao avaliar da proposta, tente novamente mais tarde");
                }
            }

        List<Proposta> lista = propostaRepository.findAllByCartaoIsNullAndStatusIs(StatusProposta.ELEGIVEL);
            System.out.println(lista.toString());

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
