package br.com.zupacademy.brenonoccioli.proposta.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class BloqueioCartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime bloqueadoEm = LocalDateTime.now();

    @NotBlank
    @Column(nullable = false)
    private String enderecoIP;

    @NotBlank
    @Column(nullable = false)
    private String userAgent;

    @NotNull
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public BloqueioCartao() {
    }

    public BloqueioCartao(String enderecoIP, String userAgent, Cartao cartao) {
        this.enderecoIP = enderecoIP;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }
}
