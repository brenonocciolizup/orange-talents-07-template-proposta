package br.com.zupacademy.brenonoccioli.proposta.controller.dto;

import br.com.zupacademy.brenonoccioli.proposta.model.StatusProposta;

public enum ResultadoSolicitacao {
    COM_RESTRICAO{
        @Override
        public StatusProposta getStatusProposta(){
            return StatusProposta.ELEGIVEL;
        }
    },
    SEM_RESTRICAO{
        @Override
        public StatusProposta getStatusProposta(){
            return StatusProposta.NAO_ELEGIVEL;
        }
    };

    public abstract StatusProposta getStatusProposta();
}
