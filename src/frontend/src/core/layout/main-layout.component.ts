import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { Router } from '@angular/router';
// import { AuthService, User } from '../services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './main-layout.component.html',
  styleUrls: ['./main-layout.component.css']
})
export class MainLayoutComponent {
  // loggedUser: User | null = null;

  loggedUser: { name: string } | null = { name: "Vitor" };

  // constructor(private authService: AuthService) {}
  constructor(private router: Router) {}

  ngOnInit() {
    this.loggedUser = this.loggedUser;
  }

  irParaHome(){
    this.router.navigate(['/home']);
  }

  irParaReceitas(){
    this.router.navigate(['/receitas']); 
  }

  irParaPerfil(){
    this.router.navigate(['/perfil']); 
  }
}
