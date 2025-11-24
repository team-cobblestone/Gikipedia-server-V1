#!/bin/bash

CONTAINER_NAME="gikipedia-app"

echo "> Stopping application container: $CONTAINER_NAME"
if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
    docker stop $CONTAINER_NAME
    echo "> Container stopped"
else
    echo "> No running container found"
fi

if [ "$(docker ps -aq -f name=$CONTAINER_NAME)" ]; then
    docker rm $CONTAINER_NAME
    echo "> Container removed"
fi

echo "> Stopping MySQL and Redis containers..."
docker compose down
echo "> All services stopped"