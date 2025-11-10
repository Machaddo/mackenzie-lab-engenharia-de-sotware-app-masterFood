import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Router } from '@angular/router';
import { User } from 'core/models/user.models';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  // Configuração
  private readonly apiUrl = 'http://localhost:8080/auth';
  private readonly TOKEN_KEY = 'auth_token';
  private readonly USER = 'user';

  // Gerenciamento de Estado de Login
  private _isLoggedIn$ = new BehaviorSubject<boolean>(this.hasToken());
  isLoggedIn$ = this._isLoggedIn$.asObservable(); // Exposto como Observable para componentes

  // Gerenciamento do Objeto do Usuário
  private _currentUserSubject = new BehaviorSubject<User | null>(this.loadUserFromLocalStorage());
  public currentUser$ = this._currentUserSubject.asObservable();

  constructor(
    private http: HttpClient, 
    private router: Router
  ) {
    console.log('AuthService inicializado. Logado:', this.hasToken());
  }

  login(credentials: any): Observable<any> {
    console.log(credentials);
    return this.http.post<any>(`${this.apiUrl}/signin`, credentials).pipe(
      tap(response => {
        localStorage.setItem(this.TOKEN_KEY, `loggedIn+${response.user.id}`)
        localStorage.setItem("userId", response.user.id);

        const user: User = response.user;
        localStorage.setItem(this.USER, JSON.stringify(user));
        
        this._isLoggedIn$.next(true);
        this._currentUserSubject.next(user);
        
        this.router.navigate(['/home']);
      })
    );
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER); 
    this._isLoggedIn$.next(false);
    this._currentUserSubject.next(null);
    this.router.navigate(['/login']);
  }

  private hasToken(): boolean {
    return !!localStorage.getItem(this.TOKEN_KEY);
  }

  public getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  public getUserId(): string | null {
    return localStorage.getItem("userId")
  }

  private loadUserFromLocalStorage(): User | null {
    const userJson = localStorage.getItem(this.USER);
    return userJson ? JSON.parse(userJson) : null;
  }

  cadastro(user: { name: string, login: string, password: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/signup`, user);
  }
}