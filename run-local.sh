#!/usr/bin/env bash

current_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# check if user is root
if [[ $EUID -ne 0 ]]; then
   echo "This script must be run as root"
   exit 1
fi

# Build Java
mvn install

cd "${current_dir}/front-end-webwinkel"
polymer build

cd "${current_dir}/front-end-commercieel-manager"
polymer build

cd "${current_dir}/front-end-inpakken"
polymer build

cd "${current_dir}"
docker-compose down --remove-orphans
docker-compose -f docker-compose.deployment.yml up --build



