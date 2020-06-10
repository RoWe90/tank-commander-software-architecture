#!/usr/bin/env bash

docker build ./PlayerModule -t player-sbt:latest
docker build ./TankModule -t tank-sbt:latest
docker build ./MainModule -t main-sbt:latest