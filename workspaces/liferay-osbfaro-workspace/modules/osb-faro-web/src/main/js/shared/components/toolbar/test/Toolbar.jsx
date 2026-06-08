/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import {Map, Set} from 'immutable';
import React from 'react';
import {withStaticRouter} from '~/test/mock-router';

import Toolbar from '../index';

jest.unmock('react-dom');

const DefaultComponent = withStaticRouter(Toolbar);

describe('Toolbar', () => {
	it('renders', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('does not render with a search input when alwaysShowSearch is false', () => {
		const {queryByPlaceholderText} = render(
			<DefaultComponent
				alwaysShowSearch={false}
				selectEntirePageIndeterminate
			/>
		);

		expect(queryByPlaceholderText('Search')).toBeNull();
	});

	it('renders with a search input when alwaysShowSearch is true', () => {
		const {getByPlaceholderText} = render(
			<DefaultComponent alwaysShowSearch selectEntirePageIndeterminate />
		);

		expect(getByPlaceholderText('Search')).toBeTruthy();
	});

	it('renders as disabled', () => {
		const {getByPlaceholderText} = render(<DefaultComponent disabled />);

		expect(getByPlaceholderText('Search')).toBeDisabled();
	});

	it('renders w/ a search query bar when there is a query', () => {
		const {container, getByText} = render(
			<DefaultComponent
				alwaysShowSearch
				query="Test"
				selectEntirePageIndeterminate
			/>
		);

		expect(getByText('Test')).toBeTruthy();
		expect(container.querySelector('.management-bar-primary')).toBeTruthy();
		expect(container.querySelector('.items-selected')).toBeTruthy();
	});

	it('renders a list of filter tags when there are active filters', () => {
		const {getByText} = render(
			<DefaultComponent
				alwaysShowSearch
				filterBy={new Map({fooField: new Set(['fooValue'])})}
				filterByOptions={[
					{
						key: 'fooField',
						values: [{label: 'fooValue', value: 'fooValue'}],
					},
				]}
				selectEntirePageIndeterminate
			/>
		);

		expect(getByText('fooValue')).toBeTruthy();
	});
});
