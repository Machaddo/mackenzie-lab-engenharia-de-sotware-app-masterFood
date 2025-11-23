import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PerfilComponent } from './perfil.component';
import { AuthService } from 'core/services/auth/auth.service';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { User } from 'core/models/user.models';
import { CommonModule } from '@angular/common';

describe('PerfilComponent (Simples)', () => {
  let component: PerfilComponent;
  let fixture: ComponentFixture<PerfilComponent>;
  let authServiceSpy: jasmine.SpyObj<AuthService>;
  let routerSpy: jasmine.SpyObj<Router>;

  const mockUser: User = { id: 1, name: 'Vitor', login: 'vitor@test.com' };

  beforeEach(async () => {
    // Mockamos o Observable currentUser$ e o método logout
    authServiceSpy = jasmine.createSpyObj('AuthService', ['logout'], {
      currentUser$: of(mockUser) // Propriedade getter mockada
    });
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      imports: [PerfilComponent, CommonModule],
      providers: [
        { provide: AuthService, useValue: authServiceSpy },
        { provide: Router, useValue: routerSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(PerfilComponent);
    component = fixture.componentInstance;
    fixture.detectChanges(); // Dispara o ngOnInit
  });

  it('deve carregar dados do usuário ao iniciar', () => {
    // O ngOnInit deve ter inscrito no currentUser$ e preenchido o formulário
    expect(component.accountForm).toBeDefined();
    expect(component.accountForm.value.name).toBe('Vitor');
    expect(component.accountForm.value.login).toBe('vitor@test.com');
  });

  it('deve chamar logout do serviço e navegar para login', () => {
    component.logout();
    expect(authServiceSpy.logout).toHaveBeenCalled();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/login']);
  });
});