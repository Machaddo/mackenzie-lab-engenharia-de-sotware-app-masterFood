# 🌟 Sistema MVC - Arquitetura

## 🌐 **Visão Geral**
Este projeto segue o padrão **MVC (Model-View-Controller)**, garantindo a separação entre:  

- **🖥 Front-end**: Interface rica e interativa em Angular  
- **⚙️ Back-end**: APIs REST em Java com Spring Boot  
- **💾 Banco de Dados**: MongoDB  
- **🔗 Integração**: JDBC para testes locais e APIs externas (LLM)  

A arquitetura é **modular, escalável e de fácil manutenção**, permitindo evolução constante sem comprometer a performance.

---

## 🏗 **Arquitetura do Sistema**

### 🖥 **Front-end**
- Desenvolvido em **Angular**  
- Baseado em **componentes reutilizáveis**  
- Comunicação com o back-end via **REST APIs**  
- Estrutura modular para facilitar manutenção e expansão futura  

### ⚙️ **Back-end**
- Desenvolvido em **Java** utilizando **Spring Boot**  
- Exposição de **APIs REST**  
- **JDBC** para testes locais  
- Integração com **APIs LLM** para funcionalidades avançadas  

### 💾 **Persistência de Dados**
- **MongoDB** como banco principal  
- Estrutura **NoSQL**, ideal para alta performance e escalabilidade  
- Permite armazenar documentos complexos e consultas rápidas  

### 🛠 **Ferramentas e Ambiente**
- **IDE IntelliJ**: suporte completo a Java, Spring Boot e Maven  
- **Maven**: gerenciamento de dependências e construção do projeto  
- **Spring Boot**: framework para criação de APIs REST  

---

## 🖼 **Diagrama de Arquitetura**

     🖥 Front-end (Angular)
     ┌────────────────────┐
     │  Componentes UI    │
     │  e Serviços        │
     └─────────┬─────────┘
               │ REST API
               ▼
     ⚙️ Back-end (Java + Spring Boot)
     ┌─────────────────────────┐
     │ Controllers & Services │
     │ Business Logic          │
     │ JDBC / LLM Integration  │
     └─────────┬─────────────┘
               │
    ┌──────────┴───────────┐
    │ 💾 MongoDB            │
    │ Document Database     │
    │ High Performance / NoSQL │
    └───────────────────────┘


### 🔄 **Fluxo de Dados**
1. O usuário interage com a **UI Angular**  
2. Angular envia requisições para o **Back-end via REST API**  
3. O **Back-end processa a lógica de negócio** e interage com o **MongoDB**  
4. Os resultados retornam para o **Front-end**  

---

## 🛠 **Tecnologias Utilizadas**
| Camada        | Tecnologia |
|---------------|------------|
| 🖥 Front-end     | Angular    |
| ⚙️ Back-end      | Java, Spring Boot |
| 💾 Banco de Dados| MongoDB    |
| 🧪 Testes Locais | JDBC       |
| 🔗 Integração    | APIs LLM   |
| 💻 IDE           | IntelliJ   |
| 📦 Gerenciador   | Maven      |

---

## 🔎 **Considerações Finais**
A arquitetura adotada garante que o sistema seja:  

- **Modular**: fácil de evoluir e manter  
- **Escalável**: preparado para lidar com aumento de usuários e dados  
- **Flexível**: permite integração com novas tecnologias sem grandes mudanças  

A separação entre front-end e back-end proporciona **independência de desenvolvimento**, garantindo maior produtividade e qualidade do código.
