import { Component, OnInit, OnDestroy } from '@angular/core';
import { DrinkService } from '../services/drink.service';
import { Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';

interface DrinkSize {
  name: string;
  price: number;
}

interface Drink {
  name: string;
  availableSizes: { [size: string]: number };
  imageUrl: string;
}

@Component({
  selector: 'app-drinks',
  standalone: true, 
  imports: [CommonModule],
  templateUrl: './drinks.component.html',
  styleUrls: ['./drinks.component.css']
  
})
export class DrinksComponent implements OnInit, OnDestroy {
  drinks: Drink[] = [];
  newDrink: Drink = {
    name: '',
    availableSizes: {},
    imageUrl: ''
  };
  sizes = ['Small', 'Medium', 'Large'];
  errorMessage: string | null = null;
  private subscriptions = new Subscription();

  constructor(private drinkService: DrinkService) {}

  ngOnInit() {
    this.loadDrinks();
  }

  loadDrinks() {
    this.errorMessage = null;
    const sub = this.drinkService.getDrinks().subscribe({
      next: (data) => this.drinks = data,
      error: (err) => this.handleError('No se pudieron cargar las bebidas.', err)
    });
    this.subscriptions.add(sub);
  }

  onSubmit() {
    this.errorMessage = null;
    this.convertPricesToNumbers();

    if (this.isFormValid()) {
      const sub = this.drinkService.addDrink(this.newDrink).subscribe({
        next: () => {
          this.loadDrinks();
          this.resetForm();
        },
        error: (err) => this.handleError('Error al agregar la bebida.', err)
      });
      this.subscriptions.add(sub);
    }
  }

  private convertPricesToNumbers() {
    Object.keys(this.newDrink.availableSizes).forEach(size => {
      this.newDrink.availableSizes[size] = Number(this.newDrink.availableSizes[size]);
    });
  }

  private isFormValid(): boolean {
    if (!this.newDrink.name.trim()) {
      this.errorMessage = 'El nombre es requerido.';
      return false;
    }
    const hasValidPrices = Object.values(this.newDrink.availableSizes).some(price => price > 0);
    if (!hasValidPrices) {
      this.errorMessage = 'Al menos un tamaño debe tener precio válido.';
      return false;
    }
    return true;
  }

  private handleError(message: string, err: any) {
    console.error(message, err);
    this.errorMessage = message;
  }

  resetForm() {
    this.newDrink = { name: '', availableSizes: {}, imageUrl: '' };
  }

  getSizes(sizes: { [size: string]: number }): DrinkSize[] {
    return Object.keys(sizes).map(key => ({ name: key, price: sizes[key] }));
  }

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }
}