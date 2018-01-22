#!/usr/bin/env bash

current_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

echo "Installing Java dependencies..."
# Build Java
mvn install -DskipTests



cd "${current_dir}/front-end-webwinkel"
echo "Installing dependencies of front-end-webwinkel..."
rm src/environment.js
cp src/environment.joris.js src/environment.js
bower install
echo "Building front-end-webwinkel..."
polymer build

cd "${current_dir}/front-end-commercieel-manager"
echo "Installing dependencies of front-end-commercieel-manager..."
rm src/environment.js
cp src/environment.joris.js src/environment.js
bower install
echo "Building front-end-commercieel-manager..."
polymer build

cd "${current_dir}/front-end-inpakken"
echo "Installing dependencies of front-end-inpakken..."
rm src/environment.js
cp src/environment.joris.js src/environment.js
bower install
echo "Building front-end-inpakken..."
polymer build

echo "Done"



