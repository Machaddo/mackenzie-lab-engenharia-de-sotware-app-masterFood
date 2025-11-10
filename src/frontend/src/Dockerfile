FROM node:18

WORKDIR /app

# 1. Copia arquivos de configuração e instala dependências
COPY package*.json ./
RUN npm install

# 2. Copia todo o restante da aplicação
COPY . .

# 3. Porta exposta
EXPOSE 4200

CMD ["npx", "ng", "serve", "--host", "0.0.0.0", "--port", "4200", "--disable-host-check"]