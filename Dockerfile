FROM hseeberger/scala-sbt
WORKDIR /tankcommander
ADD . /tankcommander
CMD sbt run