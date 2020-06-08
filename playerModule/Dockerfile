# Use the official image as a parent image.
FROM hseeberger/scala-sbt

# Inform Docker that the container is listening on the specified port at runtime.
EXPOSE 8080

# Set the working directory.
WORKDIR /tankcommander

# Copy the file from your host to your current location.
COPY src /tankcommander

# Run the specified command within the container.
CMD sbt run

