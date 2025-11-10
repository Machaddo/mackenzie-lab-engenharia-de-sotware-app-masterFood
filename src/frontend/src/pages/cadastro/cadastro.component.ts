import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from 'core/services/auth/auth.service'; 
import { HttpClient } from '@angular/common/http'; 

@Component({
  selector: 'app-cadastro',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterLink], 
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.css']
})
export class CadastroComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);
  
  errorMessage: string | null = null;
  registrationSuccess: boolean = false;

  cadastroForm = this.fb.group({
    name: ['', [Validators.required, Validators.minLength(3)]],
    username: ['', [Validators.required]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    confirmPassword: ['', [Validators.required]]
  }, { validators: this.passwordMatchValidator }); // Aplica o validador customizado

  passwordMatchValidator(form: any) {
    const password = form.get('password').value;
    const confirmPassword = form.get('confirmPassword').value;
    // Retorna null (válido) se forem iguais, ou um erro customizado ('mismatch') se forem diferentes
    return password === confirmPassword ? null : { mismatch: true };
  }

  onSubmit() {
    this.errorMessage = null;
    this.registrationSuccess = false;
    
    if (this.cadastroForm.invalid) {
      this.cadastroForm.markAllAsTouched(); 
      return;
    }

    const { name, username, password } = this.cadastroForm.value;
    this.authService.cadastro({ name: name!, login: username!, password: password! }).subscribe({
      next: () => {
        this.registrationSuccess = true;
        setTimeout(() => {
            this.router.navigate(['/login']);
        }, 3000);
      },
      error: (err) => {
        if (err.status === 409) {
           this.errorMessage = 'Este username já está em uso. Tente criar um novo.';
        } else {
           this.errorMessage = 'Erro ao tentar criar conta. Verifique sua rede.';
        }
        console.error('Erro de cadastro:', err);
      }
    });
  }
}