package com.camel.example.tutorial.tutorialspring.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

import com.camel.example.tutorial.tutorialspring.processor.ViaCepProcessor;

@Service
public class ViaCep extends RouteBuilder {

    private ViaCepProcessor viaCepProcessor = new ViaCepProcessor();
    
    @Override
    public void configure() throws Exception{
        from("direct:via-cep-call")
            .process(this.viaCepProcessor)
            .log("Response from processor: ${body}")
            .log(" CEP: ${exchangeProperty.cep}")
            .log(" nome: ${exchangeProperty.nome}")
            .toD("http://viacep.com.br/ws/"+"${exchangeProperty.cep}"+"/json/"+"?bridgeEndpoint=true")
            .log("Response from ViaCep: ${body}")
            .convertBodyTo(String.class, "UTF-8")
            .end();
    }
}

