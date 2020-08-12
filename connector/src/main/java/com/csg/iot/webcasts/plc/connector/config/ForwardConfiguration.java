package com.csg.iot.webcasts.plc.connector.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Data
@Configuration
@ConfigurationProperties("forward")
@Validated
@Component
public class ForwardConfiguration {

    @Max(4000)
    private int sampleRate;

    @NotBlank
    private String nodeId;

    @NotBlank
    private String nodeName;

}
