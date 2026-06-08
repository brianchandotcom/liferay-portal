/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map} from 'immutable';
import {RemoteData} from '~/shared/util/records';

import {actionTypes} from '../../actions/data-sources';
import reducer from '../data-sources';

describe('data-sources', () => {
	function getResult(action, id, initialiState) {
		return reducer(initialiState, action).get(String(id));
	}

	it('handles fetch data source request', () => {
		const id = 'foo';

		const action = {
			payload: {
				id,
			},
			type: actionTypes.FETCH_DATA_SOURCE_REQUEST,
		};

		expect(getResult(action, id)).toMatchObject(new RemoteData());
	});

	it('handles fetch data source failure', () => {
		const id = 'foo';

		const action = {
			payload: {
				id,
			},
			type: actionTypes.FETCH_DATA_SOURCE_FAILURE,
		};

		expect(getResult(action, id)).toMatchObject(
			new RemoteData({data: null, error: true, loading: false})
		);
	});

	it('handles delete data source success', () => {
		const id = 'foo';

		const action = {
			meta: {id},
			payload: {
				id,
			},
			type: actionTypes.DELETE_DATA_SOURCE_SUCCESS,
		};

		expect(getResult(action, id, new Map({[id]: {id}}))).toBeFalsy();
	});
});
