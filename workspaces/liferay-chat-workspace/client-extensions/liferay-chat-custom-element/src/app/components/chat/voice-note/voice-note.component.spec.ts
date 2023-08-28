/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ComponentFixture, TestBed} from '@angular/core/testing';

import {VoiceNoteComponent} from './voice-note.component';

describe('VoiceNoteComponent', () => {
	let component: VoiceNoteComponent;
	let fixture: ComponentFixture<VoiceNoteComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			declarations: [VoiceNoteComponent],
		}).compileComponents();

		fixture = TestBed.createComponent(VoiceNoteComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('creates', () => {
		expect(component).toBeTruthy();
	});
});
