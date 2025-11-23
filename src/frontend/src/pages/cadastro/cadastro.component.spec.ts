import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CadastroComponent } from './cadastro.component';
import { AuthService } from 'core/services/auth/auth.service';
import { Router, provideRouter } from '@angular/router'; // Importe provideRouter
import { ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';

describe('CadastroComponent (Simples)', () => {
  let component: CadastroComponent;
  let fixture: ComponentFixture<CadastroComponent>;
  let authServiceSpy: jasmine.SpyObj<AuthService>;
  let router: Router; // Usamos o tipo real Router, não um SpyObj manual

  beforeEach(async () => {
    // Mock do Auth Service (mantém igual)
    authServiceSpy = jasmine.createSpyObj('AuthService', ['cadastro']);

    await TestBed.configureTestingModule({
      imports: [CadastroComponent, ReactiveFormsModule],
      providers: [
        { provide: AuthService, useValue: authServiceSpy },
        
        // SOLUÇÃO: Em vez de criar mocks manuais quebrados para Router/ActivatedRoute,
        // usamos o provideRouter([]). Ele configura tudo o que o routerLink precisa automaticamente.
        provideRouter([]) 
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(CadastroComponent);
    component = fixture.componentInstance;
    
    // Injetamos o Router REAL configurado pelo provideRouter e espionamos o método navigate
    router = TestBed.inject(Router);
    spyOn(router, 'navigate'); 

    fixture.detectChanges();
  });

  it('deve criar o componente com sucesso', () => {
    expect(component).toBeTruthy();
  });

  it('NÃO deve chamar o serviço se o formulário for inválido', () => {
    component.onSubmit();
    expect(authServiceSpy.cadastro).not.toHaveBeenCalled();
  });

  it('deve chamar authService.cadastro com os dados corretos se o formulário for válido', () => {
    component.cadastroForm.setValue({
      name: 'Teste User',
      username: 'teste123',
      password: 'password',
      confirmPassword: 'password'
    });

    authServiceSpy.cadastro.and.returnValue(of({})); 

    component.onSubmit();

    expect(authServiceSpy.cadastro).toHaveBeenCalledWith({
      name: 'Teste User',
      login: 'teste123',
      password: 'password'
    });
    
    // Verificamos se o spy foi chamado
    expect(component.registrationSuccess).toBeTrue();
  });
});