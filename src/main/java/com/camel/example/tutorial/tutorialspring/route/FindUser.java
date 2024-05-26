package com.camel.example.tutorial.tutorialspring.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

@Service
public class FindUser extends RouteBuilder {
    
    @Override
    public void configure() throws Exception{
        from("direct:findUserById")
            .to("bean:userService?method=findUser(${header.id})")
            .marshal().json()
            .to("jslt:test_jslt.jslt")
            .unmarshal().json();
    }
}
