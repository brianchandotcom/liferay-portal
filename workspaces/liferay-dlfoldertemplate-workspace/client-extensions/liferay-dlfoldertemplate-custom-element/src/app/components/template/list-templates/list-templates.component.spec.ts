import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListTemplatesComponent } from './list-templates.component';

describe('ListTemplatesComponent', () => {
  let component: ListTemplatesComponent;
  let fixture: ComponentFixture<ListTemplatesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListTemplatesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListTemplatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
