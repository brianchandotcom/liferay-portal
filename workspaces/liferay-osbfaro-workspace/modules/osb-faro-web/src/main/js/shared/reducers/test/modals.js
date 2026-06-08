/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {List, Map, Set, fromJS} from 'immutable';

import {actionTypes} from '../../actions/modals';
import reducer from '../modals';

describe('Modals Reducer', () => {
	it('is a function', () => {
		expect(reducer).toBeInstanceOf(Function);
	});

	it(`should handle ${actionTypes.OPEN_MODAL}`, () => {
		const action = {
			payload: {
				closeOnBlur: true,
				props: {
					foo: 'bar',
				},
				type: 'MyModal',
			},
			type: actionTypes.OPEN_MODAL,
		};

		const state = reducer(new List(), action);

		expect(state).toEqual(
			new List([
				new Map({
					closeOnBlur: true,
					props: new Map({
						foo: 'bar',
					}),
					type: 'MyModal',
				}),
			])
		);
	});

	it('does not deeply convert modal props', () => {
		const action = {
			payload: {
				closeOnBlur: true,
				name: 'MyModal',
				props: {
					foo: Set.of(1),
				},
			},
			type: actionTypes.OPEN_MODAL,
		};

		const state = reducer(new List(), action);

		expect(state.getIn([0, 'props', 'foo'])).toEqual(Set.of(1));
	});

	it(`should handle ${actionTypes.CLOSE_MODAL}`, () => {
		const intitialState = fromJS([
			{
				name: 'Foo',
				props: {},
			},
			{
				name: 'Bar',
				props: {},
			},
		]);

		const action = {
			type: actionTypes.CLOSE_MODAL,
		};

		const state = reducer(intitialState, action);

		expect(state).toEqual(
			fromJS([
				{
					name: 'Foo',
					props: {},
				},
			])
		);
	});

	it(`should handle ${actionTypes.CLOSE_ALL_MODALS}`, () => {
		const intitialState = fromJS([
			{
				name: 'Foo',
				props: {},
			},
			{
				name: 'Bar',
				props: {},
			},
		]);

		const action = {
			type: actionTypes.CLOSE_ALL_MODALS,
		};

		const state = reducer(intitialState, action);

		expect(state).toEqual(new List());
	});
});
