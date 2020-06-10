#!/usr/bin/env bash

#this starts the "side modules"
docker-compose up -d

#this opens the "main module" in a separate console (on Manjaro Linux)
xfce4-terminal -e 'docker run -t -i --expose 8080 mainmodule:v1' -T "Tank Commander"