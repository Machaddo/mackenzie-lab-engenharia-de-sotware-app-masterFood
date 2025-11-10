import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Observable } from "rxjs";
import { RecipeResponse, RecipeResult } from "core/models/receitas.models";
import { AuthService } from "../auth/auth.service";

@Injectable({
  providedIn: "root",
})
export class ReceitasService {
  // private readonly apiUrl = "http://localhost:8080/receitas"; 
  private readonly apiUrl = "http://18.205.192.12:8080/receitas";

  constructor(
    private http: HttpClient, 
    private authService: AuthService) {
    console.log("ReceitasService inicializado.");
  }

  /**
   * Busca uma receita na API com base no termo de busca.
   * @param query O termo de busca (ex: "salada", "carbonara").
   * @returns Um Observable da estrutura de resposta da receita.
   */
  gerarReceita(query: string): Observable<RecipeResponse> {
    const body = {
      request: query,
    };

    return this.http.post<RecipeResponse>(this.apiUrl, body);
  }

  /**
   * SALVA A RECEITA ENCONTRADA NO BACKEND (POST)
   * @param recipe A receita completa a ser salva.
   * @returns Um Observable (geralmente vazio ou com o objeto salvo/ID).
   */
  salvarReceita(recipe: RecipeResult): Observable<any> {
    const url = `${this.apiUrl}/${this.authService.getUserId()}`;
    return this.http.post<any>(url, recipe);
  }

  buscarReceitasSalvas() {
    const url = `${this.apiUrl}/${this.authService.getUserId()}`;
    return this.http.get<any>(url);
  }
}
