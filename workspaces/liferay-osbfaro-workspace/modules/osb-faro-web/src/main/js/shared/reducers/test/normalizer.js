/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map} from 'immutable';
import {
	mockAccount,
	mockCSVDataSource,
	mockIndividual,
	mockProject,
	mockSegment,
} from '~/test/data';

import reducer from '../normalizer';

describe('Normalizer Reducer', () => {
	it('updates store with normalized entities', () => {
		const action = {
			payload: {
				entities: {
					accounts: {1: mockAccount(1)},
					dataSources: {2: mockCSVDataSource(2)},
					individuals: {3: mockIndividual(3)},
					projects: {15: mockProject(15)},
					segments: {4: mockSegment(4)},
				},
			},
		};

		const state = reducer(new Map(), action);

		expect(state).toMatchSnapshot();
	});

	it('returns the original state if no entities key is found', () => {
		const initialState = new Map();

		const state = reducer(initialState, {});

		expect(state).toBe(initialState);
	});

	it('returns a Map by default', () => {
		const state = reducer(undefined, {});

		expect(state).toBeInstanceOf(Map);
	});
});
