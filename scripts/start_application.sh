#!/bin/bash

IMAGE_NAME="gikipedia-server"
CONTAINER_NAME="gikipedia-app"
NETWORK_NAME="gikipedia-network"

echo "> Starting MySQL and Redis with docker compose..."
docker compose up -d
if [ $? -ne 0 ]; then
    echo "> Failed to start docker compose services"
    exit 1
fi
echo "> MySQL and Redis started successfully"

echo "> Waiting for services to be healthy..."
sleep 15

echo "> Building Docker image: $IMAGE_NAME"
docker build -t $IMAGE_NAME -f temp.dockerfile .
if [ $? -eq 0 ]; then
    echo "> Docker image built successfully"
else
    echo "> Failed to build Docker image"
    exit 1
fi

echo "> Starting application container: $CONTAINER_NAME"
docker run -d \
    --name $CONTAINER_NAME \
    --network $NETWORK_NAME \
    -e RDB_HOST=gikipedia-mysql \
    -e REDIS_HOST=gikipedia-redis \
    -p 8080:8080 \
    $IMAGE_NAME

if [ $? -eq 0 ]; then
    echo "> Application started successfully on port 8080"
else
    echo "> Failed to start application container"
    exit 1
fi