FROM adoptopenjdk/openjdk14:alpine-slim
FROM hseeberger/scala-sbt:8u212_1.2.8_2.13.0
EXPOSE 54252
WORKDIR /tankCommander
ADD /target/scala-2.12/TankModule-assembly-0.1.0-SNAPSHOT.jar /tankCommander
ENV DOCKERENV="TRUE"
ENTRYPOINT ["java", "-jar", "TankModule-assembly-0.1.0-SNAPSHOT.jar"]
# Build it.
RUN sbt compile
CMD sbt run