import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReceitasComponent } from './receitas.component';
import { ReceitasService } from 'core/services/receitas/receitas.service';
import { of } from 'rxjs';

describe('ReceitasComponent (Simples)', () => {
  let component: ReceitasComponent;
  let fixture: ComponentFixture<ReceitasComponent>;
  let receitasServiceSpy: jasmine.SpyObj<ReceitasService>;

  beforeEach(async () => {
    receitasServiceSpy = jasmine.createSpyObj('ReceitasService', ['buscarReceitasSalvas']);

    await TestBed.configureTestingModule({
      imports: [ReceitasComponent],
      providers: [
        { provide: ReceitasService, useValue: receitasServiceSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ReceitasComponent);
    component = fixture.componentInstance;
  });

  it('deve carregar receitas salvas e calcular dificuldade/tempo', () => {
    // Mock da resposta do backend
    const mockResponse = {
      recipes: [
        { 
          name: 'Receita Simples', 
          lines: [{ ingredient: 'Ovo', amount: 1, unit: 'un' }] // 1 ingrediente
        }
      ]
    };

    receitasServiceSpy.buscarReceitasSalvas.and.returnValue(of(mockResponse));

    // Executa ngOnInit
    component.ngOnInit();

    // Validações
    expect(component.receitas.length).toBe(1);
    expect(component.receitas[0].titulo).toBe('Receita Simples');
    
    // CORREÇÃO AQUI: Usamos (as any) para acessar a propriedade dinâmica 'dificuldade'
    expect((component.receitas[0] as any).dificuldade).toBe('Fácil');
  });

  it('deve abrir modal ao clicar em detalhes', () => {
    // Cria um objeto fake
    const receitaFake: any = { titulo: 'Teste', detalhes: {} };
    
    component.abrirDetalhes(receitaFake);

    expect(component.showModal).toBeTrue();
    expect(component.selectedRecipe).toBe(receitaFake);
  });
});