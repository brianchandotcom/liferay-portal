/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TestBed} from '@angular/core/testing';

import {WindowService} from './window.service';

describe('WindowService', () => {
	let service: WindowService;

	beforeEach(() => {
		TestBed.configureTestingModule({});
		service = TestBed.inject(WindowService);
	});

	it('is created', () => {
		expect(service).toBeTruthy();
	});
});
