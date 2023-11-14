import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateFoldersComponent } from './create-folders.component';

describe('CreateFoldersComponent', () => {
  let component: CreateFoldersComponent;
  let fixture: ComponentFixture<CreateFoldersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateFoldersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateFoldersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
