import { bootstrapApplication } from "@angular/platform-browser";
import { provideRouter } from "@angular/router";
import { importProvidersFrom } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { routes } from 'app.routes'
import { MainLayoutComponent } from "core/layout/main-layout.component";

// Imports para autenticação
import { 
  provideHttpClient, 
  withInterceptors 
} from '@angular/common/http';
import { 
  authInterceptor 
} from 'core/services/auth/auth.interceptor' // Importe seu Interceptor

bootstrapApplication(MainLayoutComponent, {
  providers: [
    provideRouter(routes),
    // Configuração do HttpClient com o interceptor
    provideHttpClient(
      withInterceptors([
        authInterceptor // Adiciona o interceptor aqui
      ])
    ),
    importProvidersFrom(CommonModule, FormsModule)
  ],
}).catch((err) => console.error(err));
