import { Component, mergeApplicationConfig, ApplicationConfig } from '@angular/core';
import { provideServerRendering } from '@angular/platform-server';
import { appConfig } from './app.config';
import { RouterOutlet } from '@angular/router';
import { NavComponent } from './nav/nav.component';
import { FooterComponent } from './footer/footer.component';
import { SidebarComponent } from './sidebar/sidebar.component'; // Import SidebarComponent

// Server config for server-side rendering
const serverConfig: ApplicationConfig = {
  providers: [
    provideServerRendering()
  ]
};

// Merge server config with app config
export const config = mergeApplicationConfig(appConfig, serverConfig);

// Apply the @Component decorator to the AppComponent class
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavComponent, FooterComponent, SidebarComponent], // Add SidebarComponent here
  template: `
    <app-nav></app-nav>
    <app-sidebar></app-sidebar> <!-- Ensure sidebar is included here -->
    <router-outlet></router-outlet>
    <app-footer></app-footer>
  `,
  styleUrls: ['./app.component.css'] // Ensure the styleUrls is used
})
export class AppComponent {
  title = 'share-2-teach-angular'; // Optional property for component state
}
