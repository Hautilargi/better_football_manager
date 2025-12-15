# Node 20 (LTS) slim
FROM node:20-slim

# Arbeitsverzeichnis
WORKDIR /usr/src/app

# Abhängigkeiten kopieren und installieren
COPY package*.json ./
RUN npm ci --only=production

# App-Code
COPY . .

# Port-Expose (nur Dokumentation, runtime nutzt ENV PORT)
EXPOSE 3000

# Startkommando
CMD ["node", "server.js"]
