# Use the official image as a parent image.
FROM adoptopenjdk/openjdk14:slim

# Inform Docker that the container is listening on the specified port at runtime.
EXPOSE 8080

# Set the working directory.
WORKDIR /tankcommander

# Copy the file from your host to your current location.
COPY target/scala-2.12/tankcommander_2.12-0.1.0-SNAPSHOT.jar /tankcommander

# Run the specified command within the container.
CMD java -jar tankcommander_2.12-0.1.0-SNAPSHOT.jar
