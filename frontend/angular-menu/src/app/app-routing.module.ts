import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DrinksComponent } from './drinks/drinks.component';
import { AddDrinkComponent } from './add-drink/add-drink.component'; 

const routes: Routes = [
  { path: '', component: DrinksComponent },
  { path: 'add-drink', component: AddDrinkComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}