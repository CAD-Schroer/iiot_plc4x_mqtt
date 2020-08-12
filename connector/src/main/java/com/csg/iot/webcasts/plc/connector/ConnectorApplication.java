package com.csg.iot.webcasts.plc.connector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ConnectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConnectorApplication.class, args);
    }

}
