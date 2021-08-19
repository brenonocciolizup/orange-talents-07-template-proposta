package br.com.zupacademy.brenonoccioli.proposta.validacao.annotations;

import java.util.Collection;

public class ErroPadronizadoApi {
    private Collection<String> mensagens;

    public ErroPadronizadoApi(Collection<String> mensagens){
        this.mensagens = mensagens;
    }

    public Collection<String> getMensagens() {
        return mensagens;
    }
}
