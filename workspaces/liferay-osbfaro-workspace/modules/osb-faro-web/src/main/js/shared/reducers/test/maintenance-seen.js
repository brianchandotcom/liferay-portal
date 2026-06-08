/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map} from 'immutable';
import * as data from '~/test/data';

import {actionTypes} from '../../actions/maintenance-seen';
import reducer from '../maintenance-seen';

describe('Maintenance Seen Reducer', () => {
	it('is a function', () => {
		expect(reducer).toBeInstanceOf(Function);
	});

	it(`should handle ${actionTypes.SET_MAINTENANCE_SEEN}`, () => {
		const currentUserId = '23';
		const groupId = '23';
		const stateStartDate = data.getTimestamp();

		const action = {
			payload: {
				currentUserId,
				groupId,
				stateStartDate,
			},
			type: actionTypes.SET_MAINTENANCE_SEEN,
		};

		const state = reducer(new Map(), action);

		expect(state).toEqual(
			new Map({
				[`${groupId}-${currentUserId}`]: stateStartDate,
			})
		);
	});
});
