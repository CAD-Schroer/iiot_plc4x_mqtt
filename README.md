# Open Source Gateway mit Apache PLC4X



Hier wird beispielhaft eine Verbindung zwischen Eclipse Mosquitto und einer Schneider PacDrive LMC über OPC UA hergestellt. Dazu wird Apache PLC4X verwendet.





## Getting started

Um das Beispiel zu starten benötigt man lediglich das `docker-compose.yml` aus diesem Repository. Alle  Docker-Images sind auf **hub.docker.com**. 

```bash
docker-compose up -d simulator broker
# Wait for services to start
docker-compose up connector
# In new terminal run this command
docker-compose run broker mosquitto_sub -h broker -p 1883 -t '#' -v
```



Sobald die Dienste gestartet wurden, kann man mit dem Befehl `docker-compose run broker mosquitto_sub -h broker -p 1883 -t '#' -v` auf den Eclipse Mosquitto Broker connecten und per Wildcard alle Topics abonnieren.



## Source Code

In den Verzeichnissen `connector` und `simulator` befinden sich die Projekte zu den Docker-Images. Diese können auch lokal erstellt werden. Dazu verwenden wir die Buildpack-Funktionalität von Spring-Boot.

```bash
# Build Connector
cd connector
./gradlew bootBuildImage
cd ..

# Build Simulator
cd simulator
./gradlew bootBuildImage
cd ..
```





