# Levantamento de Requisitos - Master Chef Digital

Este documento descreve os **requisitos funcionais e não-funcionais** do sistema Master Chef Digital.

## 📌 Requisitos Funcionais

O sistema deve permitir que o usuário:

1. **Informar ingredientes através de imagem**  
   - Capturar ou enviar uma foto dos ingredientes disponíveis para identificação automática.

2. **Receber sugestões de receitas**  
   - Sugestões baseadas nos ingredientes informados pelo usuário.  
   - Garantir que as receitas apresentadas sejam compatíveis com o que o usuário possui.

3. **Selecionar tipo de culinária**  
   - Filtrar receitas por culinária desejada (ex.: italiana, japonesa, brasileira, mexicana etc.).

4. **Filtrar por conteúdo calórico**  
   - Permitir escolher receitas com base em preferências calóricas, como baixo ou alto teor calórico.

5. **Cadastrar ingredientes a evitar**  
   - Evitar que receitas contendo determinados ingredientes sejam sugeridas, respeitando restrições alimentares do usuário.

---

## 📌 Requisitos Não-Funcionais

O sistema deve atender aos seguintes critérios de desempenho e qualidade:

1. **Precisão na identificação de ingredientes**  
   - Reconhecer automaticamente os ingredientes a partir de fotos com eficácia mínima de **80%**.

2. **Tempo de resposta das sugestões**  
   - Fornecer receitas compatíveis em até **5 segundos** após a identificação dos ingredientes.

3. **Acurácia das sugestões**  
   - As receitas sugeridas devem conter pelo menos **90% dos ingredientes informados** pelo usuário.

---

## 💡 Observações

- Estes requisitos servem como base para o desenvolvimento do MVP do sistema e podem ser refinados durante o ciclo de desenvolvimento.  
- Os requisitos funcionais focam na experiência do usuário e nas funcionalidades essenciais.  
- Os requisitos não-funcionais garantem **desempenho, confiabilidade e qualidade da sugestão de receitas**.
