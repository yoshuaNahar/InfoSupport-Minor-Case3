#!/usr/bin/env bash

echo "Setting environment variables..."
# Export the vars from .env into the shell:
export $(egrep -v '^#' .env | xargs)

echo "Done"
