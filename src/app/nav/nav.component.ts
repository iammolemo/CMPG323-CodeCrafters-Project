import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink } from '@angular/router';
import { UsersService } from '../users.service';

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent implements OnInit {

  constructor(private readonly usersService: UsersService){}

  isAuthenticated: boolean = false;
  isAdmin: boolean = false;
  isUser: boolean = false;
  isModerator: boolean = false;
  isEducator: boolean = false;

  ngOnInit(): void {
    this.isAuthenticated = this.usersService.isAuthenticated();
    this.isAdmin = this.usersService.isAdmin();
    this.isUser = this.usersService.isUser();
  }

  logout(): void {
    this.usersService.logOut();
    this.isAuthenticated = false;
    this.isAdmin = false;
    this.isUser = false;
  }
}
