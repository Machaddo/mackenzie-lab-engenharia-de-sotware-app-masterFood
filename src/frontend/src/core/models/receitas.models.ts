export interface RecipeLine {
  ingredient: string;
  amount: number;
  unit: string;
}

export interface RecipeResult {
  name: string;
  lines: RecipeLine[];
}

export interface RecipeResponse {
  content: RecipeResult[];
}

export interface ReceitaCard {
  id: number;
  titulo: string;
  favorito: boolean;
  detalhes: RecipeResult;
}

// Interface de resposta da lista de receitas salvas
export interface SavedRecipesResponse {
  recipes: RecipeResult[];
}