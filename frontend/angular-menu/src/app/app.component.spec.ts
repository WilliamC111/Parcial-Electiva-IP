import { TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs'; // Usado para simular valores de ActivatedRoute

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppComponent],  // Usa "imports" para componentes standalone
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { snapshot: { paramMap: of({}) } }  // Simulamos el ActivatedRoute
        }
      ]
    }).compileComponents();
  });

  it('deberia crear la app correctamente', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('deberia renderizar el componente navbar', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('app-navbar')).toBeTruthy();
  });
});
