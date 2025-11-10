import { Component, OnInit } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ReceitasService } from "core/services/receitas/receitas.service";
import { finalize } from "rxjs";
import { ReceitaCard, SavedRecipesResponse } from "core/models/receitas.models";

@Component({
  selector: "app-receitas",
  standalone: true,
  imports: [CommonModule],
  templateUrl: "./receitas.component.html",
  styleUrls: ["./receitas.component.css"],
})
export class ReceitasComponent implements OnInit {
  receitas: ReceitaCard[] = [];
  selectedRecipe: ReceitaCard | null = null;
  showModal: boolean = false;

  isLoading: boolean = false;

  constructor(private receitasService: ReceitasService) {}

  ngOnInit(): void {
    this.carregarReceitasSalvas();
  }

  carregarReceitasSalvas() {
    this.isLoading = true;

    this.receitasService
      .buscarReceitasSalvas()
      .pipe(finalize(() => (this.isLoading = false)))
      .subscribe({
        next: (response: SavedRecipesResponse) => {
          this.receitas = response.recipes.map((r, index) => {
            const qtdIngredientes = r.lines.length;

            const tempo =
              qtdIngredientes <= 4
                ? "10-15 min"
                : qtdIngredientes <= 7
                ? "20-30 min"
                : "30+ min";

            const dificuldade =
              qtdIngredientes <= 4
                ? "Fácil"
                : qtdIngredientes <= 7
                ? "Média"
                : "Avançada";

            // Lista resumida de ingredientes
            const ingredientesList = r.lines.map((l) => l.ingredient);
            const ingredientesTexto =
              ingredientesList.length <= 4
                ? ingredientesList.join(", ")
                : ingredientesList.slice(0, 4).join(", ") +
                  ` +${ingredientesList.length - 4}`;

            return {
              id: index + 1,
              titulo: r.name,
              // imagem: this.getPlaceholderImage(index),
              tempo,
              dificuldade,
              ingredientes: ingredientesTexto, // ✅ novo campo usado no HTML
              favorito: true,
              detalhes: r,
            };
          });
        },
        error: (err) => {
          console.error("Erro ao carregar receitas salvas:", err);
          alert(
            "Não foi possível carregar as receitas salvas. Verifique a conexão."
          );
        },
      });
  }

  toggleFavorito(receita: ReceitaCard) {
    receita.favorito = !receita.favorito;
    // TODO: Implementar chamada ao service para remover/desfavoritar aqui
    alert(
      `Receita ${receita.titulo} ${
        receita.favorito ? "salva" : "removida"
      }! (Chamar API)`
    );
  }

  abrirDetalhes(receita: ReceitaCard) {
    this.selectedRecipe = receita;
    this.showModal = true;
  }

  fecharModal() {
    this.showModal = false;
  }
}
