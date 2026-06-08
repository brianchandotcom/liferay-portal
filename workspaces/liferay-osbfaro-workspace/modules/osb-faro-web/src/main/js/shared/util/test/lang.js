/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DataSourceTypes, EntityTypes} from '../constants';
import {
	getDataSourceLangKey,
	getPluralMessage,
	getTypeLangKey,
	sub,
} from '../lang';

describe('sub', () => {
	it('returns an array', () => {
		const res = sub('hello world', [''], false);

		expect(res).toEqual(['hello world']);
	});

	it('returns a string', () => {
		const res = sub('hello world', ['']);

		expect(res).toEqual('hello world');
	});

	it('returns with a subbed value for {0}', () => {
		const res = sub('hello {0}', ['world']);

		expect(res).toEqual('hello world');
	});

	it('returns with multiple subbed values', () => {
		const res = sub('My name is {0} {1}', ['hello', 'world']);

		expect(res).toEqual('My name is hello world');
	});

	it('returns an array with multiple subbed values', () => {
		const res = sub('My name is {0} {1}', ['hello', 'world'], false);

		expect(res).toEqual(['My name is ', 'hello', ' ', 'world']);
	});
});

describe('getTypeLangKey', () => {
	it('langs key for account', () => {
		expect(getTypeLangKey(EntityTypes.Account)).toBe('Accounts');
	});
});

describe('getDataSourceLangKey', () => {
	it('returns a lang key for a data-source type', () => {
		expect(getDataSourceLangKey(DataSourceTypes.Liferay)).toBe(
			'Liferay DXP'
		);
	});
});

describe('getPluralMessage', () => {
	it.each`
		count        | expected
		${1}         | ${'1 person'}
		${2}         | ${'2 people'}
		${0}         | ${'0 people'}
		${23}        | ${'23 people'}
		${-1}        | ${'-1 people'}
		${undefined} | ${'0 people'}
	`(
		'should return message "$expected" for count $count',
		({count, expected}) => {
			const plural = '{0} people';
			const singular = '{0} person';

			expect(getPluralMessage(singular, plural, count)).toEqual(expected);
		}
	);

	it('returns a message with a subArray', () => {
		const plural = '{0} results found for {1}';
		const singular = '{0} result found for {1}';

		expect(
			getPluralMessage(singular, plural, 12, true, [12, 'test'])
		).toEqual('12 results found for test');

		expect(
			getPluralMessage(singular, plural, 1, true, [1, 'test'])
		).toEqual('1 result found for test');
	});
});
