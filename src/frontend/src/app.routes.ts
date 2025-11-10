import { Routes } from '@angular/router';

// Imports de Componentes Existentes
import { HomeComponent } from 'pages/home/home.component'
import { ReceitasComponent } from 'pages/receitas/receitas.component';
import { PerfilComponent } from 'pages/perfil/perfil.component';

// Imports para Autenticação
import { LoginComponent } from 'pages/login/login.component';
import { authGuard } from 'core/services/auth/auth.guard'; 
import { CadastroComponent } from 'pages/cadastro/cadastro.component';

export const routes: Routes = [

  // ROTAs PÚBLICAs 
  {
    path: 'login',
    component: LoginComponent,
  },
  
  { 
    path: 'cadastro', 
    component: CadastroComponent,
  },

  // ROTAS Privadas
  {
    path: '',
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
    
      { path: 'home', 
        component: HomeComponent 
      },
      { 
        path: 'receitas', 
        component: ReceitasComponent,
        canActivate: [authGuard]
      },
      { 
        path: 'perfil', 
        component: PerfilComponent,
        canActivate: [authGuard] 
      }
    ]
  },

  // FALLBACK
  { path: '**', redirectTo: 'home' }, 
];