#!/bin/bash

echo "> Cleaning up Docker system..."
docker system prune -af --volumes
docker compose down --rmi all --volumes --remove-orphans
echo "> Docker cleanup completed"