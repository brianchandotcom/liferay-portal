import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TemplateListHeaderComponent } from './template-list-header.component';

describe('TemplateListHeaderComponent', () => {
  let component: TemplateListHeaderComponent;
  let fixture: ComponentFixture<TemplateListHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TemplateListHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TemplateListHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
