import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { Router } from '@angular/router';
import { AuthService } from 'core/services/auth/auth.service';
import { Observable, map } from 'rxjs';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './main-layout.component.html',
  styleUrls: ['./main-layout.component.css']
})
export class MainLayoutComponent {
  userInitial$: Observable<string | null>;

  constructor(
    private router: Router,
    private authService: AuthService
  ) {
    this.userInitial$ = this.authService.currentUser$.pipe(
      map(user => user ? user.name.charAt(0).toUpperCase() : null)
    );
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

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
  
}
