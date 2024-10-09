import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OtheroersComponent } from './otheroers.component';

describe('OtheroersComponent', () => {
  let component: OtheroersComponent;
  let fixture: ComponentFixture<OtheroersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OtheroersComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OtheroersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
