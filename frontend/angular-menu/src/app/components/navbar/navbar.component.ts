import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router'; // <-- Importa estos


@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive], // <-- Añádelos aquí
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
  
})
export class NavbarComponent {}