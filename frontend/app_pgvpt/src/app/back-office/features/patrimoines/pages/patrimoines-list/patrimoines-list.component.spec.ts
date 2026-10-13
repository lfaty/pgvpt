import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatrimoinesListComponent } from './patrimoines-list.component';

describe('PatrimoinesListComponent', () => {
  let component: PatrimoinesListComponent;
  let fixture: ComponentFixture<PatrimoinesListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PatrimoinesListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PatrimoinesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
