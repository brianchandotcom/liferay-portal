/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isFSA} from 'flux-standard-action';

import {
	createLiferayDataSource,
	createSalesforceDataSource,
	deleteDataSource,
	fetchDataSource,
	updateCSVDataSource,
	updateLiferayDataSource,
	updateSalesforceDataSource,
} from '../data-sources';

describe('DataSources', () => {
	describe('createLiferayDataSource', () => {
		it('returns an action', () => {
			const action = createLiferayDataSource({});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('createSalesforceDataSource', () => {
		it('returns an action', () => {
			const action = createSalesforceDataSource({});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('deleteDataSource', () => {
		it('returns an action', () => {
			const action = deleteDataSource({});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('fetchDataSource', () => {
		it('returns an action', () => {
			const action = fetchDataSource({});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('updateCSVDataSource', () => {
		it('returns an action', () => {
			const action = updateCSVDataSource({});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('updateLiferayDataSource', () => {
		it('returns an action', () => {
			const action = updateLiferayDataSource({});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('updateSalesforceDataSource', () => {
		it('returns an action', () => {
			const action = updateSalesforceDataSource({});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});
});
