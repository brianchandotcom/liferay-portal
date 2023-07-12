import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WindowComponent } from './window.component';

describe('WindowComponent', () => {
  let component: WindowComponent;
  let fixture: ComponentFixture<WindowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WindowComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WindowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
