import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubjectviewComponent } from './subjectview.component';

describe('SubjectviewComponent', () => {
  let component: SubjectviewComponent;
  let fixture: ComponentFixture<SubjectviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubjectviewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubjectviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
