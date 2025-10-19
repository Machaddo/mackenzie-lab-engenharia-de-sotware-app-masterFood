import { Component } from "@angular/core";
import { CommonModule } from "@angular/common";
import { Router } from "@angular/router";
import { NgIf } from "@angular/common";
import { PhoneMaskDirective } from 'shared/Directives/phone-mask.directive'
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  FormArray,
  Validators,
} from "@angular/forms";

@Component({
  selector: "app-perfil",
  standalone: true,
  // Importamos o CommonModule para usar *ngIf e NgFor
  imports: [CommonModule, NgIf, PhoneMaskDirective],
  templateUrl: "./perfil.component.html",
  styleUrl: "./perfil.component.css",
})
export class PerfilComponent {
  // Variáveis para controlar a Modal
  isModalOpen: boolean = false;
  modalFormType: "conta" | "ingredientes" | null = null;

  // Dados MOCK do usuário e suas configurações
  usuario = {
    nome: "Victor Lima",
    email: "victor.lima@email.com",
    telefone: "+55 (11) 98765-4321",
    restricoes: ["Glúten", "Lactose", "Sal"],
    avatarUrl: "assets/victor-lima.jpg",
  };

  // Forms Reativos
  accountForm!: FormGroup;
  restrictionForm!: FormGroup;

  constructor(private router: Router, private fb: FormBuilder) {}

  ngOnInit(): void {
    // 1. Inicializa o formulário de Conta
    this.accountForm = this.fb.group({
      nome: [this.usuario.nome, Validators.required],
      email: [this.usuario.email, [Validators.required, Validators.email]],
      telefone: [this.usuario.telefone, Validators.required],
    });

    // 2. Inicializa o formulário de Restrições (FormArray)
    this.restrictionForm = this.fb.group({
      items: this.fb.array(
        this.usuario.restricoes.map((item) => this.createRestrictionItem(item))
      ),
    });
  }

  // Helper para criar um novo FormGroup para o FormArray
  private createRestrictionItem(nome: string = ""): FormGroup {
    return this.fb.group({
      nome: [nome, Validators.required],
    });
  }

  // Getter para facilitar o acesso ao FormArray no template
  get restricoesArray() {
    return this.restrictionForm.get("items") as FormArray;
  }

  /* ====================== */
  /* LÓGICA DA MODAL (1.1)  */
  /* ====================== */

  openEditModal(type: "conta" | "ingredientes"): void {
    this.modalFormType = type;
    this.isModalOpen = true;

    // Resetar o formulário antes de abrir (opcional, mas boa prática)
    if (type === "conta") {
      this.accountForm.patchValue(this.usuario);
    }
  }

  closeModal(): void {
    this.isModalOpen = false;
    this.modalFormType = null;
  }

  /* Lógica de Edição de Ingredientes (1.1 - FormArray) */
  addRestriction(): void {
    this.restricoesArray.push(this.createRestrictionItem());
  }

  removeRestriction(index: number): void {
    this.restricoesArray.removeAt(index);
  }

  saveChanges(formType: "conta" | "ingredientes"): void {
    const form = formType === "conta" ? this.accountForm : this.restrictionForm;

    if (form.valid) {
      // **AQUI VOCÊ CHAMARIA SEU SERVICE PARA PERSISTIR OS DADOS**

      // Atualiza os dados do usuário no componente principal (Mock)
      if (formType === "conta") {
        Object.assign(this.usuario, form.value);
      } else {
        // Mapeia o FormArray para atualizar o array original de restrições
        this.usuario.restricoes = form.value.items.map(
          (item: { nome: string }) => item.nome
        );
      }

      console.log(`Dados de ${formType} salvos!`, this.usuario);
      this.closeModal();
    } else {
      console.error("Formulário inválido!");
      form.markAllAsTouched();
    }
  }

  /* ====================== */
  /* OUTRAS FUNÇÕES         */
  /* ====================== */

  editarFoto() {
    console.log("Abrir seletor de arquivo para editar foto.");
  }

  // ... outras funções como irParaConfiguracao
  irParaConfiguracao(caminho: string) {
    // Como os cards 'Minha Conta' e 'Ingredientes' agora abrem modal,
    // esta função só será usada pelos outros cards de navegação.
    console.log(`Navegando para: ${caminho}`);
  }
}
