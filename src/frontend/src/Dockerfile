FROM node:18

WORKDIR /app

# 1. Copia arquivos de configuração e instala dependências
COPY package*.json ./
RUN npm install

# 2. Copia todo o restante da aplicação
COPY . .

# 3. Porta exposta
EXPOSE 4200

# ✅ Comando de Inicialização CORRIGIDO
# --host 0.0.0.0: Essencial para acesso externo
# --disable-host-check: Evita problemas de CORS/DNS ao acessar pelo IP da EC2
# --poll 1: (Opcional) Força a observação de arquivos, mas muitas vezes não é necessário em um container.
CMD ["npx", "ng", "serve", "--host", "0.0.0.0", "--port", "4200", "--disable-host-check"]