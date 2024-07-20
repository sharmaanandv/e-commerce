#!/bin/bash

set -e

echo "Building all modules..."

./gradlew clean build

echo "Dockerize all services..."

docker-compose build --no-cache
docker-compose up