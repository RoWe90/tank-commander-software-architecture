FROM adoptopenjdk/openjdk14:alpine-slim
FROM hseeberger/scala-sbt:8u212_1.2.8_2.13.0
EXPOSE 8080
WORKDIR /tankCommander
ADD /target/scala-2.12/MainModule-assembly-0.1.0-SNAPSHOT.jar /tankCommander
ENV DOCKERENV="TRUE"
ENV PLAYER_HOST="myplayer:54251"
ENV TANK_HOST="mytank:54252"
ENTRYPOINT ["java", "-jar", "MainModule-assembly-0.1.0-SNAPSHOT.jar"]
# Build it.
RUN sbt compile
CMD sbt run