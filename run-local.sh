#!/usr/bin/env bash

current_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# check if user is root
#if [[ $EUID -ne 0 ]]; then
#   echo "This script must be run as root"
#   exit 1
#fi

echo "Installing Java dependencies..."
# Build Java
mvn install -DskipTests

cd "${current_dir}/front-end-webwinkel"

echo "Installing dependencies of front-end-webwinkel..."
bower install

echo "Building front-end-webwinkel..."
polymer build

cd "${current_dir}/front-end-commercieel-manager"

echo "Installing dependencies of front-end-commercieel-manager..."
bower install

echo "Building front-end-commercieel-manager..."
polymer build

cd "${current_dir}/front-end-inpakken"

echo "Installing dependencies of front-end-inpakken..."
bower install

echo "Building front-end-inpakken..."
polymer build

cd "${current_dir}"
echo "Removing old containers..."
docker-compose down --remove-orphans

echo "Lifting containers..."
docker-compose -f docker-compose.deployment.yml up --build



