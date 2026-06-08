/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {Map, Set} from 'immutable';
import React from 'react';

import withFilters from '../WithFilters';

jest.unmock('react-dom');

describe('WithFilters', () => {
	afterEach(cleanup);

	it('passes filterBy to the wrapped component', () => {
		const componentSpy = jest.fn(() => <div />);

		const WrappedComponent = withFilters({filterFields: ['foo']})(
			componentSpy
		);

		render(<WrappedComponent foo="bar" />);

		jest.runAllTimers();

		expect(componentSpy).toHaveBeenCalledWith(
			expect.objectContaining({
				className: '',
				filterBy: new Map({foo: new Set(['bar'])}),
			}),
			{}
		);
	});

	it('passes filterBy to the wrapped component from the router if destructured is false', () => {
		const mockRouter = {query: {foo: 'bar'}};
		const componentSpy = jest.fn(() => <div />);

		const WrappedComponent = withFilters({
			destructured: false,
			filterFields: ['foo'],
		})(componentSpy);

		render(<WrappedComponent router={mockRouter} />);

		jest.runAllTimers();

		expect(componentSpy).toHaveBeenCalledWith(
			expect.objectContaining({
				className: '',
				filterBy: new Map({foo: new Set(['bar'])}),
				router: mockRouter,
			}),
			{}
		);
	});

	it('passes filters that are empty as an empty Set', () => {
		const componentSpy = jest.fn(() => <div />);

		const WrappedComponent = withFilters({filterFields: ['bar', 'foo']})(
			componentSpy
		);

		render(<WrappedComponent bar="baz,buz" foo="" />);

		jest.runAllTimers();

		expect(componentSpy).toHaveBeenCalledWith(
			expect.objectContaining({
				className: '',
				filterBy: new Map({
					bar: new Set(['baz', 'buz']),
					foo: new Set(),
				}),
			}),
			{}
		);
	});
});
