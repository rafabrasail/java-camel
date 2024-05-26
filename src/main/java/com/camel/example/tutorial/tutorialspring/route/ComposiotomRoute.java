package com.camel.example.tutorial.tutorialspring.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

@Service
public class ComposiotomRoute extends RouteBuilder {
    
    @Override
    public void configure() throws Exception{
        from("direct:via-cep")
        .log("Body: ${body}")
        .log("HTTP_METHOD: ${header.CamelHttpMethod}") // Log do método HTTP
        .log("HTTP_URI: ${header.CamelHttpUri}") // Log da URI completa
        .log("HTTP_PATH: ${header.CamelHttpPath}") // Log do path da URI
        .log("HTTP_QUERY: ${header.CamelHttpQuery}") // Log dos parâmetros da URL
        .log("header do cep: ${header.cep}")
        .log("header do nome: ${header.nome}")
        .doTry()
            .to("direct:via-cep-call")
            .log("Composition from ViaCep: ${body}")
        .doCatch(Exception.class)
            .log("Exception caught in route: ${exception.message}")
            .process(exchange -> {
                Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                exchange.getIn().setBody("Error occurred: " + exception.getMessage());
            })
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
        .end();
    }
}
