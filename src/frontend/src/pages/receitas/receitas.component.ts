import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // ✅ Importar o FormsModule

interface Receita {
  id: number;
  titulo: string;
  imagem: string;
  tempo: string;
  dificuldade: string;
  favorito: boolean;
}

@Component({
  selector: 'app-receitas',
  standalone: true,
  imports: [CommonModule, FormsModule], // ✅ Adicionar aqui
  templateUrl: './receitas.component.html',
  styleUrls: ['./receitas.component.css'],
})
export class ReceitasComponent {
  tituloPagina = 'Minhas Receitas';
  searchText = '';

  receitas: Receita[] = [
    {
      id: 1,
      titulo: 'Spaghetti à Carbonara',
      imagem:
        'https://images.unsplash.com/photo-1604908177522-8f0a8b1b3f9a?q=80&w=800',
      tempo: '30 min',
      dificuldade: 'Média',
      favorito: true,
    },
    {
      id: 2,
      titulo: 'Salada de Grão de Bico',
      imagem:
        'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?q=80&w=800',
      tempo: '25 min',
      dificuldade: 'Fácil',
      favorito: true,
    },
    {
      id: 3,
      titulo: 'Frango Assado com Batatas',
      imagem:
        'https://images.unsplash.com/photo-1601050690597-df6b99a8a4f3?q=80&w=800',
      tempo: '35 min',
      dificuldade: 'Média',
      favorito: true,
    },
  ];

  filtrarCategoria() {
    alert('Filtro por categoria em desenvolvimento!');
  }

  ordenarPorData() {
    alert('Ordenação por data em desenvolvimento!');
  }

  toggleFavorito(receita: Receita) {
    receita.favorito = !receita.favorito;
  }

  buscar() {
    const termo = this.searchText.trim().toLowerCase();
    if (!termo) return;
    this.receitas = this.receitas.filter((r) =>
      r.titulo.toLowerCase().includes(termo)
    );
  }
}
