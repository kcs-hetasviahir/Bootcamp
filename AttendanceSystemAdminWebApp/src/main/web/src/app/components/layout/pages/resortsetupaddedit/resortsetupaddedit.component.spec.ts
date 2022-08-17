import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResortsetupaddeditComponent } from './resortsetupaddedit.component';

describe('ResortsetupaddeditComponent', () => {
  let component: ResortsetupaddeditComponent;
  let fixture: ComponentFixture<ResortsetupaddeditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResortsetupaddeditComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResortsetupaddeditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
