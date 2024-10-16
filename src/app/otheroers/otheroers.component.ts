import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MatInputModule } from '@angular/material/input'; 
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

@Component({
  selector: 'app-other-oers',
  templateUrl: './otheroers.component.html',
  styleUrls: ['./otheroers.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule
  ]
})
export class OtherOersComponent {
  constructor(private router: Router) {}

  // Functions to navigate to various routes
  navigateToExplore() {
    this.router.navigate(['/explore']);
  }

  navigateToResearch() {
    this.router.navigate(['/research']);
  }

  navigateToDesign() {
    this.router.navigate(['/design']);
  }

  navigateToExchange() {
    this.router.navigate(['/exchange']);
  }

  navigateToResearch2() {
    this.router.navigate(['/research-2']);
  }
}
