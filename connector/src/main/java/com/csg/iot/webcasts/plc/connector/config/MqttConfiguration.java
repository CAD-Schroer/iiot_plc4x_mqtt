package com.csg.iot.webcasts.plc.connector.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Configuration
@ConfigurationProperties("mqtt")
@Validated
@Component
public class MqttConfiguration {

    @NotBlank
    private String serverURI;

}
