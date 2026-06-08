import 'whatwg-fetch';

import request, {
	addParams,
	getFormData,
	getServiceError,
	parseFromJSON,
	serializeQueryString,
	stringifyValues,
} from '../request';
import {reloadPage} from '../router';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

jest.mock('../router');

describe('addParams', () => {
	it('correctlies append query string params', () => {
		const result = addParams('http://www.test.com', {
			name: 'joe',
			title: 'blogger',
		});

		expect(result).toContain('joe');
		expect(result).toContain('blogger');
	});

	it('uses correct param separator', () => {
		const result = addParams('http://www.test.com?parameter=1', {
			name: 'joe',
		});

		expect(result.split('?').length - 1).toBe(1);
	});
});

describe('getFormData', () => {
	it('returns a FormData object', () => {
		const formData = getFormData({
			name: 'test',
		});

		expect(formData instanceof FormData).toBe(true);
	});

	it('encodes form values', () => {
		const name = 'test~!@#$%^&*()-=+_;:?><,./';

		const formData = getFormData({name});

		expect(formData.get('name')).toBe(encodeURIComponent(name));
	});
});

describe('parseFromJSON', () => {
	it('returns a parsed JSON object', () => {
		const expected = {foo: 'bar'};

		expect(parseFromJSON(JSON.stringify(expected))).toEqual(expected);
	});
});

describe('getServiceError', () => {
	it('returns a parsed service error', () => {
		const nestedError = {status: 403};

		const error = {message: JSON.stringify(nestedError)};

		expect(getServiceError(error)).toEqual(nestedError);
	});

	it('returns null if not a service error', () => {
		const nestedError = {status: 500};

		const error = {message: JSON.stringify(nestedError)};

		expect(getServiceError(JSON.stringify(error))).toBeNull();
	});
});

describe('serializeQueryString', () => {
	it('contains params', () => {
		const queryString = serializeQueryString(
			{
				name: 'test',
			},
			true
		);

		expect(queryString).toContain('name');
	});

	it('contains params', () => {
		const queryString = serializeQueryString({
			name: 'joe',
		});

		expect(queryString).toContain('joe');
	});
});

describe('request', () => {
	beforeEach(() => {
		window.fetch = jest.fn();
	});

	afterEach(() => {
		fetch.mockClear();
	});

	it('returns a Promise', () => {
		fetch.mockReturnValue(Promise.resolve(new Response()));

		const result = request({}).catch(jest.fn());

		expect(result instanceof Promise).toBe(true);
	});

	it('correctlies create request URL', () => {
		fetch.mockReturnValue(Promise.resolve(new Response('')));

		return request({
			contentType: '',
			method: 'GET',
			path: 'contacts/field_mapping',
		}).then(() => {
			const requestURL = fetch.mock.calls[0][0];

			expect(requestURL).toContain('field_mapping');
			expect(requestURL).toContain('contacts');
		});
	});

	it('updates requestURL if method is GET', () => {
		fetch.mockReturnValue(Promise.resolve(new Response('', {status: 204})));

		return request({
			data: {
				a: 2,
			},
			method: 'GET',
			path: 'contacts/field_mapping',
		}).then(() => {
			const requestURL = fetch.mock.calls[0][0];

			expect(requestURL).toContain('field_mapping?a=2');
		});
	});

	it('resolves with the parsed response', () => {
		const value = 25;

		fetch.mockReturnValue(Promise.resolve(new Response(`{"a": ${value}}`)));

		return request({}).then((data) => expect(data.a).toBe(value));
	});

	it('throws an error if the response cannot be parsed', () => {
		fetch.mockReturnValue(Promise.resolve(new Response('{test:}')));

		return request({}).catch((error) =>
			expect(error instanceof SyntaxError).toBe(true)
		);
	});

	it('rejects on a xhr status greater than or equal to 300', () => {
		fetch.mockReturnValue(Promise.resolve(new Response('', {status: 301})));

		return request({}).catch((error) =>
			expect(error instanceof Error).toBe(true)
		);
	});

	it('handles an xhr status of 204 where the response is empty', () => {
		fetch.mockReturnValue(Promise.resolve(new Response('', {status: 204})));

		return request({}).then((response) => expect(response).toEqual({}));
	});

	it('rejects on a xhr status equal to 403', () => {
		fetch.mockReturnValue(
			Promise.resolve(
				new Response('', {
					status: 403,
				})
			)
		);

		return request({}).catch((error) =>
			expect(error instanceof Error).toBe(true)
		);
	});

	it('rejects on a xhr status equal to 500 with a messageKey', () => {
		fetch.mockReturnValue(
			Promise.resolve(
				new Response('{ "messageKey": "test"}', {
					status: 500,
				})
			)
		);

		return request({}).catch((error) => {
			expect(error instanceof Error).toBe(true);
			expect(error.message).toBe('test');
		});
	});

	it('calls reloadPage on an xhr status equal to 401', () => {
		fetch.mockReturnValue(
			Promise.resolve(
				new Response('', {
					status: 401,
				})
			)
		);

		return request({}).then(() => expect(reloadPage).toBeCalled());
	});

	it('rejects if the response is not valid JSON', () => {
		fetch.mockReturnValue(
			Promise.resolve(new Response('this is definitely not json'))
		);

		return expect(request({})).rejects.toBeInstanceOf(SyntaxError);
	});
});

describe('stringifyValues', () => {
	it('serializes instances of objects', () => {
		const array = ['foo'];
		const object = {foo: 'bar'};

		const val = stringifyValues({
			arr: array,
			obj: object,
		});

		expect(val.arr).toBe(JSON.stringify(array));
		expect(val.obj).toBe(JSON.stringify(object));
	});
});
