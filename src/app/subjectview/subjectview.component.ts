import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-subject-view',
  templateUrl: './subjectview.component.html',
  styleUrls: ['./subjectview.component.css']
})
export class SubjectViewComponent {
  constructor(private router: Router) {}

  redirectToSearch() {
    this.router.navigate(['/search']); // Search button functionality
  }
}
