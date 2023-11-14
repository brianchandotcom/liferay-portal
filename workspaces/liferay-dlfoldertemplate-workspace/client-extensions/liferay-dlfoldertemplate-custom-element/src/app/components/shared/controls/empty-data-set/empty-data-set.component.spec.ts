import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmptyDataSetComponent } from './empty-data-set.component';

describe('EmptyDataSetComponent', () => {
  let component: EmptyDataSetComponent;
  let fixture: ComponentFixture<EmptyDataSetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EmptyDataSetComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmptyDataSetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
