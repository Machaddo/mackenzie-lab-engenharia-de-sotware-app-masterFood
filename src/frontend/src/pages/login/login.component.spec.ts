import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginComponent } from './login.component';
import { AuthService } from 'core/services/auth/auth.service';
import { ReactiveFormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';
import { ActivatedRoute } from '@angular/router';

describe('LoginComponent (Simples)', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authServiceSpy: jasmine.SpyObj<AuthService>;

  beforeEach(async () => {
    authServiceSpy = jasmine.createSpyObj('AuthService', ['login']);

    await TestBed.configureTestingModule({
      imports: [LoginComponent, ReactiveFormsModule],
      providers: [
        { provide: AuthService, useValue: authServiceSpy },
        // CORREÇÃO: Mock completo do ActivatedRoute para evitar erro do routerLink
        { 
          provide: ActivatedRoute, 
          useValue: { 
            snapshot: { paramMap: { get: () => null } },
            url: of([]),
            params: of({}),
            queryParams: of({}),
            fragment: of(null)
          } 
        } 
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('deve criar o componente', () => {
    expect(component).toBeTruthy();
  });

  it('deve chamar authService.login quando o formulário for submetido', () => {
    // Senha com 4 digitos para passar no Validators.minLength(4)
    const loginData = { login: 'user', senha: '1234' }; 
    component.loginForm.setValue(loginData);

    authServiceSpy.login.and.returnValue(of({ body: { user: { id: 1 } } }));

    component.onSubmit();

    expect(authServiceSpy.login).toHaveBeenCalledWith({ 
      login: 'user', 
      password: '1234' 
    });
  });

  it('deve definir mensagem de erro se o login falhar', () => {
    // Senha válida para permitir o envio
    component.loginForm.setValue({ login: 'user', senha: '1234' });

    authServiceSpy.login.and.returnValue(throwError(() => ({ status: 401 })));

    component.onSubmit();

    expect(component.errorMessage).toContain('inválidos');
  });
});