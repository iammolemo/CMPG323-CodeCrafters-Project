import { Component } from '@angular/core';
import { SidebarComponent } from '../sidebar/sidebar.component'; // Import SidebarComponent
import { RouterOutlet } from '@angular/router'; // Import RouterOutlet

@Component({
  selector: 'app-landingpage',
  standalone: true,
  imports: [RouterOutlet, SidebarComponent], // Combine all imports in one array
  templateUrl: './landingpage.component.html',
  styleUrl: './landingpage.component.css'
})
export class LandingpageComponent {

}
