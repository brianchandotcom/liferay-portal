/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
