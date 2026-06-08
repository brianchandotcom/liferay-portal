/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map, Set} from 'immutable';

import {DataSourceTypes, EntityTypes} from '../constants';
import {
	ACCOUNTS,
	INDIVIDUALS,
	LIFERAY,
	Routes,
	SEGMENTS,
	buildRoutes,
	getDataSourceType,
	getMatchedRoute,
	getRouteName,
	getType,
	removePageParam,
	removeUriQueryParam,
	resetPaginationParams,
	setUriFilterValues,
	setUriQueryValue,
	setUriQueryValues,
	toRoute,
} from '../router';

describe('setUriFilterValues', () => {
	it('adds filter queries to url and return as a string', () => {
		const mockFilterBy = new Map({
			biz: new Set(['buz']),
			foo: new Set(['bar', 'baz']),
		});

		const url = 'http://www.liferay.com';

		expect(setUriFilterValues(mockFilterBy, url)).toBe(
			'/?biz=buz&foo=bar%2Cbaz'
		);
	});
});

describe('setUriQueryValue', () => {
	it('adds query to url and return as a string', () => {
		const url = 'http://www.liferay.com';

		expect(setUriQueryValue(url, 'foo', 'bar')).toBe('/?foo=bar');

		expect(setUriQueryValue(url, 'foo')).toBe('/');
	});
});

describe('setUriQueryValues', () => {
	it('adds multiple queries to url and return as a string', () => {
		const url = 'http://www.liferay.com';

		expect(setUriQueryValues({baz: 'qux', foo: 'bar'}, url)).toBe(
			'/?baz=qux&foo=bar'
		);
	});
});

describe('getType', () => {
	it('returns type for a given route name', () => {
		expect(getType(ACCOUNTS)).toBe(EntityTypes.Account);
	});
});

describe('getDataSourceType', () => {
	it('returns a data-source type for a given route name', () => {
		expect(getDataSourceType(LIFERAY)).toBe(DataSourceTypes.Liferay);
	});
});

describe('getMatchedRoute', () => {
	it('returns the matched route', () => {
		expect(
			getMatchedRoute(
				[{route: '/foo/:id'}, {route: '/bar/:id'}],
				'/foo/123'
			)
		).toBe('/foo/:id');
	});
});

describe('getRouteName', () => {
	it('returns route name for a given type', () => {
		expect(getRouteName(EntityTypes.Account)).toBe(ACCOUNTS);
	});

	it('returns route name for the segment types', () => {
		expect(getRouteName(EntityTypes.IndividualsSegment)).toBe(SEGMENTS);
	});
});

describe('removePageParam', () => {
	it('removes page query string', () => {
		const url = 'http://www.liferay.com/';

		expect(removePageParam(null, `${url}?page=3`)).toBe('/');
	});

	it('removes page query string and set new path', () => {
		const url = 'http://www.liferay.com/';

		expect(removePageParam('/bar', `${url}foo?page=3`)).toBe('/bar');
	});
});

describe('removeUriQueryParam', () => {
	it('removes uri query param', () => {
		const href =
			'http://localhost:3000/project/33551/touchpoints/?sortField=views';
		const name = 'sortField';
		const removeURI = removeUriQueryParam(href, name);

		expect(removeURI).toEqual('/project/33551/touchpoints/');
	});
});

describe('resetPaginationParams', () => {
	it('resets the pagination parameters to the default value', () => {
		const url = 'http://www.liferay.com/';

		expect(
			resetPaginationParams(null, `${url}?page=3&orderBy=desc&query=test`)
		).toBe('/?page=1&orderBy=asc&query=');
	});
});

describe('setUriFilterValues', () => {
	it('sets the uri filter params from the filterBy Map', () => {
		const url = 'http://www.liferay.com';

		const mockFilterBy = new Map({
			devices: new Set(['desktop', 'mobile']),
			foo: new Set(['bar']),
		});

		expect(setUriFilterValues(mockFilterBy, url)).toBe(
			'/?devices=desktop%2Cmobile&foo=bar'
		);
	});
});

describe('Routes', () => {
	it('matches Routes snapshot', () => {
		expect(Routes).toBeDefined();
		expect(typeof Routes).toBe('object');
		expect(Routes.BASE).toBe('/');
		expect(Routes.WORKSPACE_WITH_ID).toBe('/workspace/:groupId([\\w._-]+)');
		expect(Routes.WORKSPACE_ADD).toBe('/workspace/add');
		expect(Routes.SETTINGS_DATA_SOURCE).toBe(
			'/workspace/:groupId([\\w._-]+)/settings/data-source/:id'
		);
		expect(Routes.CONTACTS_ENTITY).toBe(
			'/workspace/:groupId([\\w._-]+)/:channelId(\\d+)?/contacts/:type(accounts|individuals|segments)/:id'
		);
		expect(Object.keys(Routes).length).toBeGreaterThan(10);
	});
});

describe('toRoute', () => {
	it('creates a url for specific options', () => {
		const id = 123;

		const groupId = 456;

		expect(
			toRoute(Routes.CONTACTS_ENTITY, {groupId, id, type: INDIVIDUALS})
		).toBe(`/workspace/${groupId}/contacts/${INDIVIDUALS}/${id}`);

		expect(
			toRoute(Routes.CONTACTS_ENTITY, {groupId, id, type: SEGMENTS})
		).toBe(`/workspace/${groupId}/contacts/${SEGMENTS}/${id}`);
	});
});

describe('buildRoutes', () => {
	it('returns an object with keys that map to route strings', () => {
		const routes = buildRoutes({
			BAR: '/bar',
			FOO: '/foo',
		});

		expect(routes).toMatchObject({
			BAR: '/bar',
			FOO: '/foo',
		});
	});

	it('allows for nesting of routes using route objects', () => {
		const routes = buildRoutes({
			BAR: {
				path: '/bar',
				routes: {
					BAZ: '/baz',
					BIZ: '/biz',
					FIZZ: {
						path: '/fizz',
						routes: {
							BUZZ: '/buzz',
						},
					},
				},
			},
			FOO: '/foo',
		});

		expect(routes).toMatchObject({
			BAR: '/bar',
			BAZ: '/bar/baz',
			BIZ: '/bar/biz',
			BUZZ: '/bar/fizz/buzz',
			FIZZ: '/bar/fizz',
			FOO: '/foo',
		});
	});
});
