🌐 Visão Geral

Este projeto foi desenvolvido seguindo o padrão de arquitetura MVC (Model-View-Controller), com o objetivo de separar claramente a interface do usuário, a lógica de negócio e a persistência de dados. A arquitetura adotada visa escalabilidade, modularidade e facilidade de manutenção.

O sistema possui:
🖥 Front-end: Interface rica e interativa desenvolvida em Angular.
⚙️ Back-end: APIs REST desenvolvidas em Java com Spring Boot.
💾 Banco de Dados: MongoDB, garantindo alto desempenho e flexibilidade.
🔗 Integração: Suporte a JDBC para testes locais e consumo de APIs externas (LLM).

🏗 Arquitetura do Sistema
🖥 Front-end
Desenvolvido em Angular.
Baseado em componentes reutilizáveis.
Comunicação com o backend via REST APIs.
Estrutura modular para facilitar manutenção e expansão futura.

⚙️ Back-end
Desenvolvido em Java utilizando Spring Boot.
Exposição de APIs REST para comunicação com o front-end.
JDBC para testes locais e prototipagem de operações com banco.
Preparado para integração com APIs de LLM para funcionalidades avançadas.

💾 Persistência de Dados
MongoDB como banco de dados principal.
Estrutura NoSQL, ideal para alta performance e escalabilidade horizontal.
Permite armazenar documentos complexos e consultas rápidas.
🛠 Ferramentas e Ambiente de Desenvolvimento
IDE IntelliJ: suporte completo a Java, Spring Boot e Maven.
Maven: gerenciamento de dependências e construção do projeto.
Spring Boot: framework para criação rápida de APIs e serviços REST.

🛠 Tecnologias Utilizadas
Camada	Tecnologia
🖥 Front-end -> Angular
⚙️ Back-end	-> Java, Spring Boot
💾 Banco de Dados MongoDB
🧪 Testes Locais	JDBC
🔗 Integração	APIs LLM
💻 IDE	IntelliJ
📦 Gerenciador	Maven
🔎 Considerações Finais

A arquitetura proposta garante que o sistema seja modular, escalável e de fácil manutenção, permitindo a evolução constante sem comprometer a performance. A separação entre front-end e back-end permite flexibilidade no desenvolvimento e integração com novos serviços.