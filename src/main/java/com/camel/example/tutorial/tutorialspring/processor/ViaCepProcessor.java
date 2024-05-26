package com.camel.example.tutorial.tutorialspring.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class ViaCepProcessor implements Processor {
    
    @Override
    public void process(Exchange exchange) throws Exception{
        String cep = exchange.getIn().getHeader("cep", String.class);
        String nome = exchange.getIn().getHeader("nome", String.class);

        exchange.setProperty("nome", nome);
        exchange.setProperty("cep", cep);

        exchange.getIn().setBody("CEP: " + cep + ", Nome: " + nome);
    } 
}
