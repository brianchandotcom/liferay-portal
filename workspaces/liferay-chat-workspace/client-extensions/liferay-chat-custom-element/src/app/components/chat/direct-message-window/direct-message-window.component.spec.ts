import {ComponentFixture, TestBed} from '@angular/core/testing';

import {DirectMessageWindowComponent} from './direct-message-window.component';

describe('DirectMessageWindowComponent', () => {
	let component: DirectMessageWindowComponent;
	let fixture: ComponentFixture<DirectMessageWindowComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			declarations: [DirectMessageWindowComponent],
		}).compileComponents();

		fixture = TestBed.createComponent(DirectMessageWindowComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
