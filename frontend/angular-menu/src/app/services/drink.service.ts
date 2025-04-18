import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root' // Esto permite inyección global
})
export class DrinkService {
  private apiUrl = 'http://localhost:8000/menu';

  constructor(private http: HttpClient) {}

  // Obtener todas las bebidas
  getDrinks(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  // Agregar una nueva bebida
  addDrink(drink: any): Observable<any> {
    return this.http.post(this.apiUrl, drink);
  }
}