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
FROM eclipse-temurin:21-jre
WORKDIR /app


# Spring Boot JAR
COPY --from=backend-build /backend/target/*.jar app.jar


# Vite Build → Spring Boot Static Resources
COPY --from=frontend-build /frontend/dist /app/static


ENV SPRING_WEB_RESOURCES_STATIC_LOCATIONS=classpath:/static/,file:/app/static/


EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]