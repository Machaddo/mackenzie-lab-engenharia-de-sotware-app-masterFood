FROM node:18

WORKDIR /app

# Copia somente o package.json para instalar dependências primeiro
COPY package*.json ./
RUN npm install

# Copia todo o restante da aplicação
COPY . .

# Angular dev server precisa escutar em todas interfaces no Docker
EXPOSE 4200

CMD ["npx", "ng", "serve", "--host", "0.0.0.0", "--port", "4200"]