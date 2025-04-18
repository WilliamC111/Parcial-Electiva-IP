import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

interface Drink {
  name: string;
  availableSizes: { [key: string]: number }; // Ej: { "Small": 3.50, "Medium": 4.00 }
  imageUrl: string;
}

interface SizeOption {
  name: string;
  price: number;
}

@Component({
  selector: 'app-drinks',
  standalone: true,
  imports:  [CommonModule, HttpClientModule], // Aquí puedes importar otros módulos o componentes si es necesario
  templateUrl: './drinks.component.html',
  styleUrls: ['./drinks.component.css'],
})
export class DrinksComponent implements OnInit {
  drinks: Drink[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.getDrinks();
  }

  getDrinks() {
    this.http.get<Drink[]>('http://localhost:8000/menu').subscribe(
      (data) => {
        this.drinks = data;
      },
      (error) => {
        console.error('Error al obtener las bebidas:', error);
      }
    );
  }

  // Convierte el objeto availableSizes en un array para *ngFor
  getSizes(sizes: { [key: string]: number }): SizeOption[] {
    return Object.keys(sizes).map(key => ({
      name: key,
      price: sizes[key]
    }));
  }
}