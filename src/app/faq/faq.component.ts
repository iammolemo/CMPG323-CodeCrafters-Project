import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgFor } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-faq',
  templateUrl: './faq.component.html',
  styleUrl: './faq.component.css',
  standalone: true,
  imports: [CommonModule]

})
export class FaqComponent {
  // Array of FAQs
  faqs = [
    { 
      question: 'What is Share2Teach?', 
      answer: 'Share2Teach is a global platform for educators and learners to share educational resources.' 
    },
    { 
      question: 'How do I upload documents?', 
      answer: 'You can upload documents from the dashboard by clicking the "Upload" button and selecting the file.' 
    },
    { 
      question: 'Can I edit or delete my content?', 
      answer: 'Yes, you can manage your uploaded content directly from the dashboard by selecting the content and using the edit or delete options.' 
    },
    { 
      question: 'Who can access the resources?', 
      answer: 'Resources are available to anyone with an Open Access account, moderated by educators and admins.' 
    },
    { 
      question: 'How do I contact support?', 
      answer: 'You can contact support via the "Help" section in your dashboard or email us at support@share2teach.com.' 
    }
  ];

  // Variable to track the visibility of answers
  selectedQuestionIndex: number | null = null;

  // Function to toggle visibility of an answer
  toggleAnswer(index: number) {
    this.selectedQuestionIndex = this.selectedQuestionIndex === index ? null : index;
  }
}
