package com.camel.example.tutorial.tutorialspring.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.camel.example.tutorial.tutorialspring.model.User;

import static org.apache.camel.model.rest.RestParamType.body;
import static org.apache.camel.model.rest.RestParamType.path;

@Component
public class CamelRoute extends RouteBuilder{

    @Autowired
    private Environment env;

    @Override
    public void configure() throws Exception {
        restConfiguration()
            .component("servlet")
            .bindingMode(RestBindingMode.auto)
            .dataFormatProperty("prettyPrint", "true")
            .enableCORS(true)
            .port(env.getProperty("server.port", "8080"))
            .apiContextPath("/api-doc")
            .apiProperty("api.title", "User API")
            .apiProperty("api.version", "1.0.0");

        rest("/users").description("User REST service")
            .consumes("application/json")
            .produces("application/json")

            .get().description("Find all users").outType(User[].class)
                .responseMessage().code(200).message("All users successfully returned").endResponseMessage()
                .to("bean:userService?method=findUsers")
                

            .get("/{id}").description("Find user by ID")
                .outType(User.class)
                .param()
                    .name("id")
                    .type(path)
                    .description("The ID of the user")
                    .dataType("integer")
                .endParam()
                .responseMessage().code(200).message("User successfully returned").endResponseMessage()
                .to("direct:findUserById")
                // .to("bean:userService?method=findUser(${header.id})")

            .put("/{id}").description("Update a user").type(User.class)
                .param().name("id").type(path).description("The ID of the user to update").dataType("integer").endParam()    
                .param().name("body").type(body).description("The user to update").endParam()
                .responseMessage().code(204).message("User successfully updated").endResponseMessage()
                .to("direct:update-user");
        
        rest("/correios").description("User REST service")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .produces(MediaType.APPLICATION_JSON_VALUE)
               
                .get().description("api de roteamento de rota")
                .param()
                    .name("cep")
                    .type(path)
                    .description("cep no via cep")
                    .type(RestParamType.query)
                .endParam()
                .param()
                    .name("nome")
                    .type(path)
                    .description("nome chave trat")
                    .type(RestParamType.query)
                .endParam()
                .to("direct:via-cep")

                .responseMessage().code(200).message(HttpStatus.OK.getReasonPhrase()).endResponseMessage()
                .responseMessage().code(400).message(HttpStatus.BAD_REQUEST.getReasonPhrase()).endResponseMessage()
                .responseMessage().code(401).message(HttpStatus.UNAUTHORIZED.getReasonPhrase()).endResponseMessage()
                .responseMessage().code(403).message(HttpStatus.FORBIDDEN.getReasonPhrase()).endResponseMessage()
                .responseMessage().code(404).message(HttpStatus.NOT_FOUND.getReasonPhrase()).endResponseMessage()
                .responseMessage().code(500).message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).endResponseMessage()
                .responseMessage().code(504).message(HttpStatus.GATEWAY_TIMEOUT.getReasonPhrase()).endResponseMessage();


    }
    
}
