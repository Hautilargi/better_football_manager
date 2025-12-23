#!/bin/bash

# Start Spring Boot im Hintergrund
java -jar /app/backend.jar &

# Start Nginx im Vordergrund
nginx -g "daemon off;"