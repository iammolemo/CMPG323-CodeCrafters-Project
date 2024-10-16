import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
  standalone: true,
  imports: [FormsModule, CommonModule]
})
export class SearchComponent {
  searchQuery: string = '';
  selectedTag: string = '';
  selectedFileType: string = '';
  availableTags: string[] = ['Tag1', 'Tag2', 'Tag3']; 
  availableFileTypes: string[] = ['pdf', 'doc', 'txt'];  
  searchResults: any[] = [];
  searchPerformed: boolean = false;

  constructor(private http: HttpClient) {}

  // Perform a search for files
  onSearch() {
    this.searchPerformed = true;
  
    const token = localStorage.getItem('token'); // Retrieve JWT token from localStorage
    if (!token) {
      console.error('User is not authenticated');
      return;
    }
  
    let searchUrl = `http://localhost:8080/api/search?name=${encodeURIComponent(this.searchQuery)}`;
    if (this.selectedTag) {
      searchUrl += `&tags=${encodeURIComponent(this.selectedTag)}`;
    }
    if (this.selectedFileType) {
      searchUrl += `&type=${encodeURIComponent(this.selectedFileType)}`;
    }

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    this.http.get(searchUrl, { headers }).subscribe((results: any) => {
      this.searchResults = results;
    }, (error) => {
      console.error('Search failed', error);
      if (error.status === 403) {
        alert('You do not have permission to access this resource.');
      } else {
        alert('An error occurred while searching. Please try again later.');
      }
    });
  }

  // Download the selected file
  onDownloadFile(name: string) {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    this.http.get(`http://localhost:8080/auth/downloadFile/${encodeURIComponent(name)}`, { headers, responseType: 'blob' })
      .subscribe((blob: Blob) => {
        const link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.download = name; // Use the file name for download
        link.click();
      }, error => {
        console.error('File download failed', error);
      });
  }

  // View the selected file online
  onViewFile(name: string) {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    this.http.get(`http://localhost:8080/auth/viewFile/${encodeURIComponent(name)}`, { headers, responseType: 'blob' })
      .subscribe((blob: Blob) => {
        const url = window.URL.createObjectURL(blob);
        window.open(url); // Opens the file in a new tab or window
      }, error => {
        console.error('File view failed', error);
      });
  }
}
