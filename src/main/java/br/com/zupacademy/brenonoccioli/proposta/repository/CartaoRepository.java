package br.com.zupacademy.brenonoccioli.proposta.repository;

import br.com.zupacademy.brenonoccioli.proposta.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}
