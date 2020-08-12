package com.csg.iot.webcasts.plc.connector.services;

import com.csg.iot.webcasts.plc.connector.config.PlcConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.plc4x.java.PlcDriverManager;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.exceptions.PlcConnectionException;
import org.apache.plc4x.java.api.messages.PlcReadRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class PlcManager {

    private PlcConnection plcConnection;
    private final PlcConfiguration plcConfiguration;

    public PlcManager(
            PlcConfiguration plcConfiguration
    ) {
        this.plcConfiguration = plcConfiguration;
    }

    public boolean isConnected() {
        return (Objects.nonNull(this.plcConnection)) && this.plcConnection.isConnected();
    }

    public PlcConnection connect() {

        if (this.isConnected()) {
            return this.plcConnection;
        }

        log.info(
                "###> Connecting to PLC at {}",
                this.plcConfiguration.getConnectionString()
        );

        try {
            this.plcConnection = new PlcDriverManager()
                    .getConnection(this.plcConfiguration.getConnectionString());

            log.info("###> Successfully connected to PLC");

        } catch (PlcConnectionException plcConnectionException) {
            log.error("###> PLC Exception: {}", plcConnectionException.getMessage());
            throw new RuntimeException(plcConnectionException);
        }

        return this.plcConnection;

    }

    public PlcReadRequest.Builder getReadRequestBuilder() {
        return this.plcConnection.readRequestBuilder();
    }

}
