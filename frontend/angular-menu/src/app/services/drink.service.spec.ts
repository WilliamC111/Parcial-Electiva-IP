import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DrinkService } from './drink.service';

describe('Servicio de ApiPython :D', () => {
  let service: DrinkService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],  // Asegúrate de que esté importado correctamente
      providers: [DrinkService]
    });
    service = TestBed.inject(DrinkService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('el servicio esta funcionando correctamente', () => {
    expect(service).toBeTruthy();
  });

  afterEach(() => {
    // Verificar que no haya solicitudes pendientes
    httpTestingController.verify();
  });
});
