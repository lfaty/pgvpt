import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GeolocalisationFormComponent } from './geolocalisation-form.component';

describe('GeolocalisationFormComponent', () => {
  let component: GeolocalisationFormComponent;
  let fixture: ComponentFixture<GeolocalisationFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GeolocalisationFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GeolocalisationFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
