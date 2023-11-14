import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TemplateListItemComponent } from './template-list-item.component';

describe('TemplateListItemComponent', () => {
  let component: TemplateListItemComponent;
  let fixture: ComponentFixture<TemplateListItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TemplateListItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TemplateListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
