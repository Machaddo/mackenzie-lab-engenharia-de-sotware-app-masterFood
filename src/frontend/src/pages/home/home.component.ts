import { Component } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";

interface Card {
  id: number;
  title: string;
  subtitle?: string;
  img: string;
  badge?: string;
  description?: string;
  saved?: boolean;
}

@Component({
  selector: "app-home",
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.css"],
})
export class HomeComponent {
  title = "MasterFood";
  searchText = "";

  cards: Card[] = [
    {
      id: 1,
      title: "Receita do Dia",
      subtitle: "Spaghetti Carbonara",
      img: "https://images.unsplash.com/photo-1604908177522-8f0a8b1b3f9a?q=80&w=800&auto=format&fit=crop&ixlib=rb-4.0.3&s=1",
      badge: "NOVO!",
      description:
        "Uma receita clássica italiana com ovos, queijo e bacon crocante.",
    },
    {
      id: 2,
      title: "Explorar Categorias",
      subtitle: "Massas, Saladas, Snacks",
      img: "https://images.unsplash.com/photo-1543353071-087092ec393a?q=80&w=800&auto=format&fit=crop&ixlib=rb-4.0.3&s=2",
      description: "Encontre receitas por categoria.",
    },
    {
      id: 3,
      title: "Tendências Culinárias",
      subtitle: "Pão com Abacate",
      img: "https://images.unsplash.com/photo-1550507995-3b0e9f65b8c3?q=80&w=800&auto=format&fit=crop&ixlib=rb-4.0.3&s=3",
      description: "Dicas rápidas e tendências do momento.",
    },
  ];

  activeCard: Card | null = null;
  shoppingList: string[] = [];

  search() {
    // lógica simples: filtrar cards por título/subtitle
    const q = this.searchText.trim().toLowerCase();
    if (!q) {
      alert("Digite algo para buscar.");
      return;
    }
    const results = this.cards.filter((c) =>
      (c.title + " " + (c.subtitle || "")).toLowerCase().includes(q)
    );
    if (results.length === 0) {
      alert("Nenhuma receita encontrada para: " + this.searchText);
    } else {
      this.activeCard = results[0];
    }
  }

  openCard(card: Card) {
    this.activeCard = card;
  }

  closeCard() {
    this.activeCard = null;
  }

  toggleSave(card: Card) {
    card.saved = !card.saved;
  }

  addToShopping(card: Card) {
    this.shoppingList.push(card.title);
    alert(card.title + " adicionado à sua lista de compras.");
  }
}
