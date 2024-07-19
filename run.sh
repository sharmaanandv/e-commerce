#!/bin/bash

echo "Building all modules..."
./gradlew clean

./gradlew :api-gateway:build
./gradlew :auth-service:build
./gradlew :order-service:build
./gradlew :product-service:build

docker-compose up