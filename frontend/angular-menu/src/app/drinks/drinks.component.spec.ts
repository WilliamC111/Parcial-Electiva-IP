import { DrinkService } from '../services/drink.service';
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';


describe('DrinkService', () => {
  let service: DrinkService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],  // Asegúrate de incluir HttpClientTestingModule aquí
      providers: [DrinkService]
    });
    service = TestBed.inject(DrinkService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('debería ser creado', () => {
    expect(service).toBeTruthy();
  });

  afterEach(() => {
    // Verificar que no haya solicitudes pendientes
    httpTestingController.verify();
  });
});
