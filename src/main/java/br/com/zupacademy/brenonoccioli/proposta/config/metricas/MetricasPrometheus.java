package br.com.zupacademy.brenonoccioli.proposta.config.metricas;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MetricasPrometheus {

    private final MeterRegistry meterRegistry;

    public MetricasPrometheus(MeterRegistry meterRegistry){
        this.meterRegistry = meterRegistry;
    }

    public void contadorDePropostas(){
        List<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("emissora", "Bandeira X"));
        tags.add(Tag.of("banco", "Orange Talents"));

        Counter contador = this.meterRegistry.counter("proposta-criada", tags);
        contador.increment();
    }

    public void contaFalhaCriacaoDeProposta(){
        List<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("emissora", "Bandeira X"));
        tags.add(Tag.of("banco", "Orange Talents"));

        Counter contador = this.meterRegistry.counter("proposta-criada-erro", tags);
        contador.increment();
    }

    public void contaFalhaAssociacaoCarteira(){
        List<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("emissora", "Bandeira X"));
        tags.add(Tag.of("banco", "Orange Talents"));

        Counter contador = this.meterRegistry.counter("proposta-erro-carteira", tags);
        contador.increment();
    }

}
