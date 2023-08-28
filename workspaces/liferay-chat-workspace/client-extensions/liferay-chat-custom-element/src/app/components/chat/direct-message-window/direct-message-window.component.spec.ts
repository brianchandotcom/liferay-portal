/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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

	it('creates', () => {
		expect(component).toBeTruthy();
	});
});
