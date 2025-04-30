import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AddDrinkComponent } from './add-drink.component';
import { DrinkService } from '../services/drink.service';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';

describe('AddDrinkComponent', () => {
  let component: AddDrinkComponent;
  let fixture: ComponentFixture<AddDrinkComponent>;
  let drinkServiceSpy: jasmine.SpyObj<DrinkService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    const drinkSpy = jasmine.createSpyObj('DrinkService', ['addDrink']);
    const routerMock = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      imports: [AddDrinkComponent],
      providers: [
        { provide: DrinkService, useValue: drinkSpy },
        { provide: Router, useValue: routerMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AddDrinkComponent);
    component = fixture.componentInstance;
    drinkServiceSpy = TestBed.inject(DrinkService) as jasmine.SpyObj<DrinkService>;
    routerSpy = TestBed.inject(Router) as jasmine.SpyObj<Router>;
    fixture.detectChanges();
  });

  it('debería crear el componente', () => {
    expect(component).toBeTruthy();
  });

  it('debería agregar bebida correctamente y redirigir al menú', () => {
    drinkServiceSpy.addDrink.and.returnValue(of({}));

    component.onSubmit();

    expect(drinkServiceSpy.addDrink).toHaveBeenCalled();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/']);
  });

  it('debería manejar error al agregar bebida', () => {
    drinkServiceSpy.addDrink.and.returnValue(throwError(() => new Error('Error de API')));

    component.onSubmit();

    expect(drinkServiceSpy.addDrink).toHaveBeenCalled();
    expect(component.isLoading).toBeFalse();
  });
});
