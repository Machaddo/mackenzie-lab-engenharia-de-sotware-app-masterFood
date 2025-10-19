import { Routes } from '@angular/router';
import { MainLayoutComponent } from 'core/layout/main-layout.component';
import { HomeComponent } from 'pages/home/home.component'
import { ReceitasComponent } from 'pages/receitas/receitas.component';
import { AppComponent } from './app.component';
import { PerfilComponent } from 'pages/perfil/perfil.component';

export const routes: Routes = [
  {
    path: '',
    component: AppComponent,
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' }, // redireciona ao iniciar
      { path: 'home', component: HomeComponent },
      { path: 'receitas', component: ReceitasComponent },
      { path: 'perfil', component: PerfilComponent },
    ]
  },
  { path: '**', redirectTo: 'home' }, // fallback para rotas inválidas
];