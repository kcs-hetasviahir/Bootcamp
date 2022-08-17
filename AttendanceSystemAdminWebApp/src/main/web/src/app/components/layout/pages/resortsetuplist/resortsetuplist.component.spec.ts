import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResortsetuplistComponent } from './resortsetuplist.component';

describe('ResortsetuplistComponent', () => {
  let component: ResortsetuplistComponent;
  let fixture: ComponentFixture<ResortsetuplistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResortsetuplistComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResortsetuplistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
