import sendRequest from '~/shared/util/request';

import api, {CALL_API, toAction} from '../api';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

jest.mock('~/shared/util/request');

describe('API Middleware', () => {
	it('calls next middleware if not an API call', () => {
		sendRequest.setResponseData({foo: 'bar'});

		const next = jest.fn();

		const action = {
			type: 'TEST_REQUEST',
		};

		api()(next)(action);

		expect(next).toBeCalledWith(action);
	});

	it('returns a promise', () => {
		const action = {
			meta: {
				[CALL_API]: {
					data: {foo: 'bar'},
					method: 'GET',
					path: 'test/path',
					types: ['TEST_REQUEST', 'TEST_SUCCESS', 'TEST_FAILURE'],
				},
				schema: 'schema',
			},
			type: 'NO_OP',
		};

		const next = jest.fn();

		const result = api()(next)(action);

		expect(result instanceof Promise).toBe(true);
	});

	it('calls the next middleware with an action', () => {
		const action = {
			meta: {
				[CALL_API]: {
					data: {foo: 'bar'},
					method: 'GET',
					path: 'test/path',
					types: ['TEST_REQUEST', 'TEST_SUCCESS', 'TEST_FAILURE'],
				},
				schema: 'schema',
			},
			type: 'NO_OP',
		};

		const next = jest.fn();

		api()(next)(action);

		expect(next.mock.calls[0][0].type).toBeTruthy();
	});

	it('calls the next middleware with the successful action', () => {
		const action = {
			meta: {
				[CALL_API]: {
					data: {foo: 'bar'},
					method: 'GET',
					path: 'test/path',
					types: ['TEST_REQUEST', 'TEST_SUCCESS', 'TEST_FAILURE'],
				},
				schema: 'schema',
			},
			type: 'NO_OP',
		};

		const next = jest.fn();

		return api()(next)(action).then((val) =>
			expect(val.payload.foo).toBe('bar')
		);
	});

	it('calls the next middleware with the failure action', () => {
		const action = {
			meta: {
				[CALL_API]: {
					data: {foo: 'bar'},
					method: 'GET',
					path: 'test/path',
					types: ['TEST_REQUEST', 'TEST_SUCCESS', 'TEST_FAILURE'],
				},
				schema: 'schema',
			},
			type: 'NO_OP',
		};

		const next = jest.fn();

		return api()(next)(action).catch((val) =>
			expect(val.reason).toBe('bar')
		);
	});

	it('calls requestFn if requestFn exists', () => {
		const requestFn = jest.fn().mockReturnValue(Promise.resolve(''));

		const action = {
			meta: {
				[CALL_API]: {
					data: {foo: 'bar'},
					requestFn,
					types: ['TEST_REQUEST', 'TEST_SUCCESS', 'TEST_FAILURE'],
				},
				schema: 'schema',
			},
			type: 'NO_OP',
		};

		const next = jest.fn();

		return api()(next)(action).then(() => expect(requestFn).toBeCalled());
	});
});

describe('toAction', () => {
	it('returns an action object', () => {
		const actionType = 'TEST';

		const action = toAction(actionType, {meta: {}});

		expect(action.type).toBe(actionType);
	});

	it('does not contain api call data', () => {
		const action = toAction('TEST', {
			meta: {
				[CALL_API]: 1,
				id: 15,
			},
		});

		expect(action.meta[CALL_API]).toBeUndefined();
	});
});
