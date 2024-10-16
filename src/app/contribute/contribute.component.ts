import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';  
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-contribute',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './contribute.component.html',
  styleUrls: ['./contribute.component.css']
})
export class ContributeComponent {
  contributeForm: FormGroup;
  selectedFile: File | null = null;

  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.contributeForm = this.fb.group({
      subject: ['', Validators.required],
      grade: ['', Validators.required],
      tags: ['', Validators.required],
    });
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  onSubmit() {
    if (this.contributeForm.valid && this.selectedFile) {
      const formData = new FormData();
      formData.append('file', this.selectedFile, this.selectedFile.name);
      formData.append('subject', this.contributeForm.value.subject);
      formData.append('grade', this.contributeForm.value.grade);
      formData.append('tags', this.contributeForm.value.tags);

      // Retrieve the token from localStorage
      const token = localStorage.getItem('token');

      this.http.post('http://localhost:8080/contributor/upload-file', formData, {
        headers: {
          'Authorization': `Bearer ${token}` // Include the token in the headers
        }
      })
      .subscribe(response => {
        console.log('File uploaded successfully', response);
        // Reset the form after successful submission
        this.contributeForm.reset();
        this.selectedFile = null;
      }, error => {
        console.error('Error uploading file', error);
      });
    } else {
      console.error('Form is invalid or file is not selected');
    }
  }
}
