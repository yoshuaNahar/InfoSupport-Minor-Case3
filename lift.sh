#!/usr/bin/env bash

echo "Lifting Kantilever environment..."
docker-compose -f docker-compose.kantilever.yml up -d --remove-orphans

echo "Lifting De Jong environment..."
docker-compose up -d

echo "Done..."
