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
rm environment.js
cp environment.joris.js environment.js
bower install

echo "Building front-end-webwinkel..."
polymer build






cd "${current_dir}/front-end-commercieel-manager"

echo "Installing dependencies of front-end-commercieel-manager..."
rm environment.js
cp environment.joris.js environment.js
bower install

echo "Building front-end-commercieel-manager..."
polymer build

cd "${current_dir}/front-end-inpakken"

echo "Installing dependencies of front-end-inpakken..."
rm environment.js
cp environment.joris.js environment.js
bower install

echo "Building front-end-inpakken..."
polymer build

cd "${current_dir}"
echo "Removing old containers..."
docker-compose down --remove-orphans

echo "Lifting kantilever environment..."
docker login minordotnet.azurecr.io --username "a1236a8c-9c84-4ca3-b15c-6ff8ef6e1d8b" --password "Vqox9jmjWkA6BlkXlaUG"
docker network create proxy_network
docker network create kantilever_network
docker-compose -f docker-compose.kantylever.yml up --build -d

echo "Lifting containers..."
docker-compose -f docker-compose.deployment.yml up --build



