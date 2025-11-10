import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from 'core/services/auth/auth.service'; 
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterLink], 
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  
  errorMessage: string | null = null;

  loginForm = this.fb.group({
    login: ['', [Validators.required, ]],
    senha: ['', [Validators.minLength(4)]]
  });

  onSubmit() {
    this.errorMessage = null;
    
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched(); 
      return;
    }

    const { login, senha } = this.loginForm.value;
    
    this.authService.login({ login: login!, password: senha! }).subscribe({
      next: () => {
        console.log('Login bem-sucedido. Redirecionando...');
      },
      error: (err) => {
        if (err.status === 401) {
           this.errorMessage = 'E-mail ou senha inválidos. Tente novamente.';
        } else {
           this.errorMessage = 'Ocorreu um erro ao tentar conectar. Verifique sua rede.';
        }
        console.error('Erro de login:', err);
      }
    });
  }
}