/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map} from 'immutable';

import {actionTypes} from '../../actions/sidebar';
import reducer from '../sidebar';

describe('Sidebar Reducer', () => {
	it('is a function', () => {
		expect(reducer).toBeInstanceOf(Function);
	});

	it(`should handle ${actionTypes.COLLAPSE_SIDEBAR}`, () => {
		const currentUserId = '23';
		const collapsed = true;

		const action = {
			payload: {
				collapsed,
				currentUserId,
			},
			type: actionTypes.COLLAPSE_SIDEBAR,
		};

		const state = reducer(new Map(), action);

		expect(state).toEqual(
			new Map({
				[currentUserId]: collapsed,
			})
		);
	});
});
