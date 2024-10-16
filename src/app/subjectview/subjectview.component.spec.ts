import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SubjectViewComponent } from './subjectview.component';

describe('SubjectViewComponent', () => {
  let component: SubjectViewComponent;
  let fixture: ComponentFixture<SubjectViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SubjectViewComponent] // Ensure this is correct
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubjectViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
