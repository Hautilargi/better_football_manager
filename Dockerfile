# ---------- Frontend Build ----------
FROM node:20 AS frontend-build
WORKDIR /frontend
# Abhängigkeiten installieren
COPY frontend/package.json frontend/package-lock.json* ./
RUN npm install
# Frontend-Code kopieren & bauen
COPY frontend ./
RUN npm run build


# ---------- Backend Build ----------
FROM maven:3.9-eclipse-temurin-21 AS backend-build
WORKDIR /backend
COPY backend/pom.xml ./
RUN mvn dependency:go-offline
COPY backend ./
RUN mvn package -DskipTests


# ---------- Runtime ----------
# 3. Final Image mit Nginx + Spring Boot
FROM openjdk:17-jdk-slim
WORKDIR /app

# Spring Boot JAR
COPY --from=backend-build /backend/target/*.jar ./backend.jar


# Vite Build → Spring Boot Static Resources
COPY --from=frontend-build /frontend/dist ./frontend/build


# Nginx
RUN apt-get update && apt-get install -y nginx && rm -rf /var/lib/apt/lists/*
COPY nginx/default.conf /etc/nginx/conf.d/default.conf

# Start Script
COPY start.sh /start.sh
RUN chmod +x /start.sh

EXPOSE 80
EXPOSE 8080

CMD ["/start.sh"]