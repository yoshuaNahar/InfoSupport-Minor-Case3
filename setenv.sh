#!/usr/bin/env bash

echo "Setting environment variables..."
# Export the vars from .env into the shell:
export $(egrep -v '^#' .env | xargs)

# Indicates that the environment has run first time setup
if [ ! -f ops/.envIsSetup ]; then

# Docker Login
echo "Logging in to the docker-registry..."
docker login minordotnet.azurecr.io --username ${DOCKER_USER} --password ${DOCKER_PASS}

echo "Creating docker networks..."
docker network create proxy_network
docker network create kantilever_network

# Create file indicating the first time setup ran.
touch ops/.envIsSetup
else

echo "Skipping first time setup..."
fi

echo "Done"
