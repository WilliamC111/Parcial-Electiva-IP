import { Component } from '@angular/core';
import { DrinkService } from '../services/drink.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule], 
  selector: 'app-add-drink',
  templateUrl: './add-drink.component.html',
  styleUrls: ['./add-drink.component.css']
})
export class AddDrinkComponent {
  isLoading = false;
  newDrink: any = {
    name: '',
    availableSizes: { Small: null, Medium: null, Large: null },
    imageUrl: ''
  };
  sizes = ['Small', 'Medium', 'Large'];

  constructor(
    private drinkService: DrinkService,
    private router: Router
  ) {}

  onSubmit() {
    this.isLoading = true;
    this.drinkService.addDrink(this.newDrink).subscribe({
      next: () => {
        this.isLoading = false;
        this.router.navigate(['/']);
      },
      error: (err) => {
        this.isLoading = false;
        console.error('Error:', err);
      }
    });
  }
}