import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModerateComponent } from './moderate.component';

describe('ModerateComponent', () => {
  let component: ModerateComponent;
  let fixture: ComponentFixture<ModerateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModerateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModerateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
