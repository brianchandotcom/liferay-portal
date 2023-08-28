/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AttachmentViewComponent} from './attachment-view.component';

describe('AttachmentViewComponent', () => {
	let component: AttachmentViewComponent;
	let fixture: ComponentFixture<AttachmentViewComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			declarations: [AttachmentViewComponent],
		}).compileComponents();

		fixture = TestBed.createComponent(AttachmentViewComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('creates', () => {
		expect(component).toBeTruthy();
	});
});
