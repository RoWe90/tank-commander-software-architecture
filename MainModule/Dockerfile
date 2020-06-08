# Use the official image as a parent image.
FROM hseeberger/scala-sbt:graalvm-ce-20.0.0-java11_1.3.12_2.13.2

RUN mkdir -p /MainModule

ADD . /MainModule

WORKDIR /MainModule

CMD sbt run


