import { Routes } from '@angular/router';
import { DrinksComponent } from './drinks/drinks.component';
import { AddDrinkComponent } from './add-drink/add-drink.component';

export const routes: Routes = [
  { path: '', component: DrinksComponent, pathMatch: 'full' },
  { path: 'add-drink', component: AddDrinkComponent }
];