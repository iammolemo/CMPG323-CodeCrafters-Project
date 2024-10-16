import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OtherOersComponent } from './otheroers.component';

describe('OtheroersComponent', () => {
  let component: OtherOersComponent;
  let fixture: ComponentFixture<OtherOersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OtherOersComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OtherOersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
