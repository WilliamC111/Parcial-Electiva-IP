import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { DrinksComponent } from './drinks/drinks.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [],
  imports: [
    BrowserModule,
    FormsModule,
    AppComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
