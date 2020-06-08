#!/usr/bin/env bash

sbt assembly

docker build . -t tankcommander-root 

docker run tankcommander-root:latest