import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms'; 
import { MatInputModule } from '@angular/material/input'; 
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { NgModule } from '@angular/core';

@Component({
  selector: 'app-analytics',
  templateUrl: './analytics.component.html',
  styleUrls: ['./analytics.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule
  ]
})
export class AnalyticsComponent {
  uploadForm: FormGroup;
  courses = [
    { title: 'Introduction to Collaborative Learning Strategies', overview: 'Overview of collaboration in learning.', duration: '1 day' },
    { title: 'Project-Based Learning', overview: 'Dive into project-based learning.', duration: '4 weeks' },
    { title: 'Study Planning and Time Management', overview: 'Workshop on time management.', duration: '5 days' },
    { title: 'Enhancing Self-Directed Learning with Technology', overview: 'Tools for self-directed learning.', duration: '2 weeks' },
    { title: 'Introduction to Open Educational Resources', overview: 'Basics of OER.', duration: '6 days' }
  ];

  constructor(private fb: FormBuilder, private router: Router) {
    this.uploadForm = this.fb.group({
      contentTitle: [''],
      file: [null]
    });
  }

  onFileSelect(event: any) {
    const file = event.target.files[0];
    this.uploadForm.patchValue({ file: file });
    this.uploadForm.get('file')?.updateValueAndValidity();
  }

  onSubmit() {
    console.log(this.uploadForm.value);
  }
}
