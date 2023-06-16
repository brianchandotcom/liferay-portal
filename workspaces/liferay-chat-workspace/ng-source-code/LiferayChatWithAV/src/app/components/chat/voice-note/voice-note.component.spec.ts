import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VoiceNoteComponent } from './voice-note.component';

describe('VoiceNoteComponent', () => {
  let component: VoiceNoteComponent;
  let fixture: ComponentFixture<VoiceNoteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VoiceNoteComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VoiceNoteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
