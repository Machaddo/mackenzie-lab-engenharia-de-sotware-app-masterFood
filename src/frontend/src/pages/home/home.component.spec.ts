import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HomeComponent } from './home.component';
import { ReceitasService } from 'core/services/receitas/receitas.service';
import { AuthService } from 'core/services/auth/auth.service';
import { FormsModule } from '@angular/forms';
import { of } from 'rxjs';

describe('HomeComponent (Simples)', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let receitasServiceSpy: jasmine.SpyObj<ReceitasService>;
  let authServiceSpy: jasmine.SpyObj<AuthService>;

  beforeEach(async () => {
    receitasServiceSpy = jasmine.createSpyObj('ReceitasService', ['gerarReceita', 'salvarReceita']);
    authServiceSpy = jasmine.createSpyObj('AuthService', ['getUserId']);

    await TestBed.configureTestingModule({
      imports: [HomeComponent, FormsModule],
      providers: [
        { provide: ReceitasService, useValue: receitasServiceSpy },
        { provide: AuthService, useValue: authServiceSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('deve buscar receita e preencher a variável receitaEncontrada', () => {
    const mockResponse = { content: [{ name: 'Bolo', lines: [] }] };
    receitasServiceSpy.gerarReceita.and.returnValue(of(mockResponse));
    
    component.searchText = 'Bolo';
    component.buscarReceita();

    expect(receitasServiceSpy.gerarReceita).toHaveBeenCalledWith('Bolo');
    expect(component.receitaEncontrada).toBeDefined();
    expect(component.receitaEncontrada?.name).toBe('Bolo');
  });

  it('NÃO deve salvar receita se usuário não estiver logado (getUserId null)', () => {
    authServiceSpy.getUserId.and.returnValue(null);
    
    component.salvarReceita();

    // Verifica se exibiu o toast de aviso/erro
    expect(component.isToastVisible).toBeTrue();
    // Garante que não chamou o serviço de salvar
    expect(receitasServiceSpy.salvarReceita).not.toHaveBeenCalled();
  });

  it('deve salvar receita se usuário estiver logado', () => {
    // Simula usuário logado (retorna ID '1')
    authServiceSpy.getUserId.and.returnValue('1');
    // Prepara uma receita na tela
    component.receitaEncontrada = { name: 'Teste', lines: [] };
    
    receitasServiceSpy.salvarReceita.and.returnValue(of({}));

    component.salvarReceita();

    expect(receitasServiceSpy.salvarReceita).toHaveBeenCalled();
    expect(component.receitaEncontrada?.saved).toBeTrue();
  });
});