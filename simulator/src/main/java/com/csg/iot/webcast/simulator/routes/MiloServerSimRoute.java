package com.csg.iot.webcast.simulator.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.milo.server.MiloServerEndpoint;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class MiloServerSimRoute extends RouteBuilder {

    private static final String ENDPOINT = "milo-server:lrBeltDriveSpeed";

    @Override
    public void configure() throws Exception {

        var miloServerComponent = getContext()
                .getEndpoint(ENDPOINT, MiloServerEndpoint.class).getComponent();

        miloServerComponent.setEnableAnonymousAuthentication(true);
        miloServerComponent.setBindAddresses("0.0.0.0");
        miloServerComponent.setPort(12686);

        from("timer://plc")
                .setBody(exchange -> new Random().nextInt(1000))
                .to(ENDPOINT);

    }

}
