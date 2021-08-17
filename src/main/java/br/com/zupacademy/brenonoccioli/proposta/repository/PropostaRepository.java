package br.com.zupacademy.brenonoccioli.proposta.repository;

import br.com.zupacademy.brenonoccioli.proposta.model.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    Optional<Proposta> findByDocumento(String documento);
}
