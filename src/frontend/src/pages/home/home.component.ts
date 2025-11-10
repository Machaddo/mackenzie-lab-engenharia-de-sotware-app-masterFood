import { Component } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RecipeResponse, RecipeResult } from "core/models/receitas.models";
import { ReceitasService } from "core/services/receitas/receitas.service";
import { finalize } from "rxjs";
import { AuthService } from "core/services/auth/auth.service";

@Component({
  selector: "app-home",
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.css"],
})
export class HomeComponent {
  searchText: string;
  receitaEncontrada: (RecipeResult & { saved?: boolean }) | null = null;
  isToastVisible: boolean = false;
  toastMessage: string;
  toastIconClass: string;
  isLoading: boolean = false;

  constructor(
    private receitasService: ReceitasService,
    private authService: AuthService
  ) {}

  buscarReceita() {
    console.log("teste");
    const q = this.searchText.trim();

    this.receitaEncontrada = null;

    if (!q) {
      alert("Digite algo para buscar.");
      return;
    }

    this.isLoading = true;

    this.receitasService
      .gerarReceita(q)
      .pipe(finalize(() => (this.isLoading = false)))
      .subscribe({
        next: (response: RecipeResponse) => {
          if (response.content && response.content.length > 0) {
            this.receitaEncontrada = {
              ...response.content[0],
              saved: false,
            };
          } else {
            alert(`Nenhuma receita encontrada para: ${q}`);
          }
        },
        error: (err) => {
          console.error("Erro ao buscar receita:", err);
          alert("Erro ao buscar receita. Tente novamente mais tarde.");
        },
      });
  }

  salvarReceita() {
    const userId = this.authService.getUserId();

    if (!userId) {
      this.showToast(
        "Faça o login para salvar uma receita.",
        "bi bi-lock-fill text-danger me-2"
      );
      return;
    } 

    if (!this.receitaEncontrada) return;

    const isSaving = !this.receitaEncontrada.saved;

    if (isSaving) {
      this.receitasService.salvarReceita(this.receitaEncontrada).subscribe({
        next: () => {
          this.receitaEncontrada!.saved = true;
          this.showToast(
            `Receita '${this.receitaEncontrada!.name}' salva!`,
            "bi-heart-fill text-danger"
          );
        },
        error: (err) => {
          console.error("Erro ao salvar receita:", err);
          this.showToast(
            "Erro ao salvar receita. Tente novamente.",
            "bi-x-octagon-fill text-danger"
          );
        },
      });
    } else {
      this.receitaEncontrada.saved = false;
      this.showToast(
        `Receita '${this.receitaEncontrada.name}' removida dos favoritos.`,
        "bi-heart-broken text-muted"
      );
    }
  }

  private showToast(message: string, iconClass: string) {
    this.toastMessage = message;
    this.toastIconClass = iconClass;
    this.isToastVisible = true;

    setTimeout(() => {
      this.isToastVisible = false;
    }, 3000);
  }

  formatAmount(amount: number, unit: string): string {
    if (amount === 0.0 && unit === "") {
      // Caso de "a gosto"
      return "";
    }
    // Garante que haja um espaço antes da unidade se ela não estiver vazia
    return `${amount}${unit.trim() ? " " + unit.trim() : ""}`;
  }
}
