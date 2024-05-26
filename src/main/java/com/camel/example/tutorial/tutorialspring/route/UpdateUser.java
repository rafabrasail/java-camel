package com.camel.example.tutorial.tutorialspring.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

@Service
public class UpdateUser extends RouteBuilder {
    
    @Override
    public void configure() throws Exception {
        from("direct:update-user")
            .to("bean:userService?method=updateUser")
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204))
            .setBody(constant(""));
    }
}
