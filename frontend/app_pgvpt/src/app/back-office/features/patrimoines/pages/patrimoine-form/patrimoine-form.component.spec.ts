import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatrimoineFormComponent } from './patrimoine-form.component';

describe('PatrimoineFormComponent', () => {
  let component: PatrimoineFormComponent;
  let fixture: ComponentFixture<PatrimoineFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PatrimoineFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PatrimoineFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
