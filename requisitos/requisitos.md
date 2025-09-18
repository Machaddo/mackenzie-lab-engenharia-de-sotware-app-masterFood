# Levantamento de Requisitos - Master Chef Digital

Este documento descreve os **requisitos funcionais e não-funcionais** do sistema Master Chef Digital.

## 📌 Requisitos Funcionais

O sistema deve permitir que o usuário:

1. **Informar ingredientes através de texto**  
   - Usuario deve possuir um campo de texto para mencionar que receita deseja buscar. Podendo informar os itens que possui e aquilo que deseja como resultado final

2. **Receber sugestões de receitas**  
   - Sugestões baseadas nos ingredientes informados pelo usuário.  
   - Garantir que as receitas apresentadas sejam compatíveis com o que o usuário possui.

3. **Selecionar tipo de culinária**  
   - Filtrar receitas por culinária desejada (ex.: italiana, japonesa, brasileira, mexicana etc.).

5. **Cadastrar ingredientes a evitar**  
   - Evitar que receitas contendo determinados ingredientes sejam sugeridas, respeitando restrições alimentares do usuário.

---

## 📌 Requisitos Não-Funcionais

O sistema deve atender aos seguintes critérios de desempenho e qualidade:

1. **Precisão na identificação de ingredientes**  
   - Reconhecer automaticamente os ingredientes a partir de texto com eficácia mínima de **80%**.

2. **Tempo de resposta das sugestões**  
   - Fornecer receitas compatíveis em até **5 segundos** após a identificação dos ingredientes.

3. **Acurácia das sugestões**  
   - As receitas sugeridas devem conter pelo menos **90% dos ingredientes informados** pelo usuário.

---

## 💡 Observações

- Estes requisitos servem como base para o desenvolvimento do MVP do sistema e podem ser refinados durante o ciclo de desenvolvimento.  
- Os requisitos funcionais focam na experiência do usuário e nas funcionalidades essenciais.  
- Os requisitos não-funcionais garantem **desempenho, confiabilidade e qualidade da sugestão de receitas**.