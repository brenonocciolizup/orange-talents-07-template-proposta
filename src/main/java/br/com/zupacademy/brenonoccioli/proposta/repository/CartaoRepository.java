package br.com.zupacademy.brenonoccioli.proposta.repository;

import br.com.zupacademy.brenonoccioli.proposta.model.Cartao;
import br.com.zupacademy.brenonoccioli.proposta.model.StatusCartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
    List<Cartao> findAllByStatus(StatusCartao statusCartao);
}
