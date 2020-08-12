package com.csg.iot.webcasts.plc.connector.services;

import com.csg.iot.webcasts.plc.connector.config.MqttConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class MqttManager {

    private String clientId;

    private IMqttClient mqttClient;
    private MqttConfiguration mqttConfiguration;
    private MqttConnectOptions mqttConnectOptions;

    public MqttManager(
            MqttConfiguration mqttConfiguration
    ) {
        this.mqttConfiguration = mqttConfiguration;
        this.clientId = String.format("webcast.connector.%s", UUID.randomUUID().toString());

        mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);

        this.connect(
                this.mqttConfiguration.getServerURI()
        );

    }

    private void connect(String serverURI) {
        if (isConnected()) return;

        log.info(
                "###> Connecting to MQTT Broker at {}",
                this.mqttConfiguration.getServerURI()
        );

        try {
            this.mqttClient = new MqttClient(serverURI, clientId);
            this.mqttClient.connect(mqttConnectOptions);
            log.info("###> Successfully connected to MQTT Broker");
        } catch (MqttException mqttException) {
            log.error("###> MQTT Exception: {}", mqttException.getMessage());
        }
    }

    private boolean isConnected() {
        return (Objects.nonNull(this.mqttClient)) && mqttClient.isConnected();
    }

    public void publish(String topic, String msg) {
        var mqttMessage = new MqttMessage(msg.getBytes());

        try {
            mqttClient.publish(topic, mqttMessage);
        } catch (MqttException e) {
            log.warn(
                    "Could not publish message '{}' to topic {} ",
                    mqttMessage,
                    topic
            );
        }
    }

}
