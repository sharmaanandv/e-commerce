#!/bin/bash

echo "Building all modules..."
./gradlew clean build

docker-compose up