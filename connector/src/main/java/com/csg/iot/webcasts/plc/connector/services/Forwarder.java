package com.csg.iot.webcasts.plc.connector.services;

import com.csg.iot.webcasts.plc.connector.config.ForwardConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.plc4x.java.api.messages.PlcReadRequest;
import org.apache.plc4x.java.api.messages.PlcReadResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class Forwarder {

    MqttManager mqttManager;
    PlcManager plcManager;

    PlcReadRequest plcReadRequest;

    ForwardConfiguration configuration;

    private final String nodeId;
    private final String nodeName;

    public Forwarder(
            MqttManager mqttManager,
            PlcManager plcManager,
            ForwardConfiguration forwardConfiguration
    ) {
        this.mqttManager = mqttManager;
        this.plcManager = plcManager;

        this.configuration = forwardConfiguration;

        this.nodeId = this.configuration.getNodeId();
        this.nodeName = this.configuration.getNodeName();
    }

    /**
     * Read from PLC and publish to MQTT
     */
    @Scheduled(fixedRateString = "${forward.sampleRate:4000}")
    public void readAndPublish() {

        if (!this.plcManager.isConnected()) {
            this.plcManager.connect();
            this.plcReadRequest = this.plcManager.getReadRequestBuilder()
                    .addItem(nodeName, nodeId)
                    .build();
            return;
        }

        CompletableFuture<? extends PlcReadResponse> asyncReadResponse = this.plcReadRequest.execute();

        asyncReadResponse.whenComplete((response, throwable) -> {

            String value = response.getString(nodeName);
            log.info("PLC Value of {}: {}", nodeName, value);
            mqttManager.publish(nodeName, value);

        });
    }

}
