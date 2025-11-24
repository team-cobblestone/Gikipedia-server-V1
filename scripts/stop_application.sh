#!/bin/bash

CONTAINER_NAME="gikipedia-app"

echo "> Stopping Docker container: $CONTAINER_NAME"
if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
    echo "> Stopping running container..."
    docker stop $CONTAINER_NAME
    echo "> Container stopped"
else
    echo "> No running container found"
fi

if [ "$(docker ps -aq -f name=$CONTAINER_NAME)" ]; then
    echo "> Removing container..."
    docker rm $CONTAINER_NAME
    echo "> Container removed"
else
    echo "> No container to remove"
fi

echo "> Stopping MySQL and Redis with docker compose..."
docker compose down
echo "> Docker compose services stopped"