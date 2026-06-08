/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map} from 'immutable';

import {RemoteData} from '../records';
import {handleError, handleLoading} from '../redux';

describe('redux', () => {
	describe('handleLoading', () => {
		it('sets loading on RemoteData record', () => {
			expect(handleLoading(new Map(), {payload: {id: 23}})).toEqual(
				new Map().set(23, new RemoteData({loading: true}))
			);
		});
	});

	describe('handleError', () => {
		it('sets error on RemoteData record', () => {
			expect(handleError(new Map(), {payload: {id: 23}})).toEqual(
				new Map().set(23, new RemoteData({error: true, loading: false}))
			);
		});
	});
});
