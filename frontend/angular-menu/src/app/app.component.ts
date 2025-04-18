import { Component } from '@angular/core';
import { DrinksComponent } from './drinks/drinks.component'; // Importa el componente DrinksComponent

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [DrinksComponent], // Importa componentes hijos directamente
  template: `
    <app-drinks></app-drinks>
  `
})
export class AppComponent {}

