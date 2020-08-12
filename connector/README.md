# Connector

```
export MQTT_SERVERURI="tcp://192.9.200.141:1883"
export PLC_CONNECTIONSTRING="opcua:tcp://172.25.2.171:4840/SchneiderElectric/Uaserver"
export PLC_NODENAME=IOT_lrBeltDriveSpeed
export FORWARD_NODEID="ns=2;s=items-lrBeltDriveSpeed"
export FORWARD_NODENAME="lrBeltDriveSpeed"
```

## Build Docker Container

```bash
./gradlew clean build bootBuildImage -x test
```