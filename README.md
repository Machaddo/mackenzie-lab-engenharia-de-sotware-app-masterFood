# 👨‍🍳 Master Chef Digital

![Angular](https://img.shields.io/badge/frontend-Angular_17-red) ![Java](https://img.shields.io/badge/backend-Spring_Boot-green) ![MongoDB](https://img.shields.io/badge/database-MongoDB-green)

O **Master Chef Digital** é uma aplicação inteligente que sugere receitas culinárias baseadas nos ingredientes que o usuário possui em casa. Utilizando o padrão de arquitetura **MVC**, o sistema integra uma interface moderna em Angular com um backend robusto em Java Spring Boot e Inteligência Artificial (LLM) para gerar sugestões precisas.

---

## 📑 Índice
- [Sobre o Projeto](#-sobre-o-projeto)
- [Funcionalidades](#-funcionalidades)
- [Arquitetura e Tecnologias](#-arquitetura-e-tecnologias)
- [Pré-requisitos](#-pré-requisitos)
- [Manual de Instalação](#-manual-de-instalação)
  - [Configuração do Backend](#1-backend-java--spring-boot)
  - [Configuração do Frontend](#2-frontend-angular)
- [Como Utilizar](#-como-utilizar)
- [Documentação Adicional](#-documentação-adicional)

---

## 📖 Sobre o Projeto
O objetivo do sistema é reduzir o desperdício de alimentos e auxiliar usuários na decisão do que cozinhar. O sistema permite informar ingredientes via texto, filtrar por tipo de culinária e restringir ingredientes indesejados (alergias ou dietas).



## 🚀 Funcionalidades

Conforme o levantamento de requisitos, o sistema permite:
* **Busca Inteligente:** Informar ingredientes que possui em casa através de texto livre.
* **Sugestão de Receitas:** Receber opções compatíveis (mínimo 90% de match com os ingredientes).
* **Filtros de Culinária:** Selecionar tipos específicos (Italiana, Japonesa, Brasileira, etc.).
* **Performance:** Respostas em até 5 segundos após a identificação dos itens.



## 🏗 Arquitetura e Tecnologias

O projeto segue uma arquitetura modular baseada em camadas:

### 🖥️ Front-end (Cliente)
* **Framework:** Angular 17
* **Gerenciador de Pacotes:** NPM
* **Comunicação:** REST API

### ⚙️ Back-end (Servidor)
* **Linguagem:** Java
* **Framework:** Spring Boot (Web, Data)
* **Build Tool:** Maven
* **Integração:** JDBC (Testes) e APIs de LLM (IA Generativa)

### 💾 Persistência
* **Banco de Dados:** postgreSQL 

---

## 📦 Pré-requisitos

Antes de começar, certifique-se de ter instalado em sua máquina:
* **Node.js** (Versão 18 ou superior recomendada para Angular 17)
* **Java JDK** (Versão 17 ou superior)
* **Maven**
* **PostgreSQL** (Instalado localmente ou via Docker) 
* **Git**

---

## 🛠 Manual de Instalação

### 1. Backend (Java + Spring Boot)

1.  Navegue até a pasta do backend:
    ```bash
    cd backend
    ```
2.  Configure o banco de dados e chaves de API:
    * Abra o arquivo `src/main/resources/application.properties`.
    * Insira sua string de conexão do MongoDB e a chave da API de LLM.
    ```properties
    spring.data.mongodb.uri=mongodb://localhost:27017/masterchefdb
    api.llm.key=SUA_CHAVE_AQUI
    ```
3.  Instale as dependências e compile o projeto:
    ```bash
    mvn clean install
    ```
4.  Execute a aplicação:
    ```bash
    mvn spring-boot:run
    ```
    *O servidor iniciará geralmente na porta `8080`.*

### 2. Frontend (Angular)

1.  Navegue até a pasta do frontend:
    ```bash
    cd frontend
    ```
2.  Instale as dependências do projeto (Node Modules):
    ```bash
    npm install
    ```
3.  Inicie o servidor de desenvolvimento:
    ```bash
    npm start
    ```
    *Ou utilize `ng serve` se tiver o Angular CLI instalado globalmente.*
4.  Acesse a aplicação no navegador:
    * URL Padrão: `http://localhost:4200`

---

## 📱 Como Utilizar

### 1. Acesso e Criação de Conta
1.  **Acesse a Home:** Abra o navegador em `http://localhost:4200`.
2.  **Crie uma Conta (Obrigatório para Salvar uma receita):**
    * No canto superior direito, clique em **"Entrar"** ou **"Cadastrar"**.
    * Preencha seus dados (Nome, Nome Usuario e Senha) no formulário de registro.
    * Após o cadastro, faça o login para habilitar todas as funcionalidades do sistema.

### 2. Buscar e Salvar Receitas
1.  **Informe os Ingredientes:** Na página inicial, no campo de texto principal, digite os itens que você possui (ex: "Tenho frango, batata e natas").
2.  **Busque:** Clique no botão de buscar. O sistema retornará cards com as sugestões geradas pela IA.
3.  **Salvar Receita:**
    * Gostou de uma sugestão? Clique no ícone de **Favorito (❤️)** 
    * *Nota:* Se não estiver logado, o sistema solicitará que faça o login neste momento.

### 3. Gerenciar Minhas Receitas
1.  **Acessar Lista:** No menu de navegação, clique em **"Minhas Receitas"**. Aqui você verá todas as sugestões que salvou anteriormente.
2.  **Ver Detalhes:** Clique sobre qualquer card na sua lista salva para expandir a visualização.
3.  **Modo de Preparo:** Na visão detalhada, você terá acesso à lista completa de ingredientes e ao passo a passo detalhado do modo de preparo.

---

## 📚 Documentação Adicional

A documentação completa do sistema encontra-se na pasta `/docs` deste repositório:

* **[Arquitetura](./docs/arquitetura/readme.md):** Detalhes sobre o padrão MVC e diagramas de componentes.
* **[Requisitos](./docs/requisitos/requisitos.md):** Lista detalhada de requisitos funcionais e não-funcionais.
* **[Modelagem](./docs/modelagem/diagramas.md):** Diagramas de Caso de Uso, Classes e Sequência.
* **[Protótipo (Figma)](./docs/prototipo/link.txt):** Link para o design visual das telas.
  
