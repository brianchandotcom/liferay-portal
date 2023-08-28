/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ComponentFixture, TestBed} from '@angular/core/testing';

import {WindowComponent} from './window.component';

describe('WindowComponent', () => {
	let component: WindowComponent;
	let fixture: ComponentFixture<WindowComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			declarations: [WindowComponent],
		}).compileComponents();

		fixture = TestBed.createComponent(WindowComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('creates', () => {
		expect(component).toBeTruthy();
	});
});
