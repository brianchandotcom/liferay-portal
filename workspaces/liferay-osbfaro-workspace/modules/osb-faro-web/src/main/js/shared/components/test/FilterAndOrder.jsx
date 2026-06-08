/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import FilterAndOrder from '../FilterAndOrder';

jest.unmock('react-dom');

describe('FilterAndOrder', () => {
	afterEach(cleanup);

	const FILTER_BY_OPTIONS = [
		{
			key: 'status',
			label: 'status',
			values: [
				{label: 'done', value: 'done'},
				{label: 'running', value: 'running'},
			],
		},
	];

	const ORDER_BY_OPTIONS = [
		{
			label: 'name',
			value: 'name',
		},
	];

	it('renders', () => {
		const {getByTestId} = render(
			<FilterAndOrder filterByOptions={FILTER_BY_OPTIONS} />
		);

		expect(getByTestId('filter-button')).toBeInTheDocument();
	});

	it('renders with filter by options', () => {
		const {container} = render(
			<FilterAndOrder filterByOptions={FILTER_BY_OPTIONS} />
		);

		expect(
			container.querySelector('[data-testid="filter-button"]')
		).toBeTruthy();
	});

	it('renders with order by options', () => {
		const {container} = render(
			<FilterAndOrder orderByOptions={ORDER_BY_OPTIONS} />
		);

		expect(
			container.querySelector('[data-testid="order-button"]')
		).toBeTruthy();
	});

	it('renders with order by options and filter by options', () => {
		const {container} = render(
			<FilterAndOrder
				filterByOptions={FILTER_BY_OPTIONS}
				orderByOptions={ORDER_BY_OPTIONS}
			/>
		);

		expect(
			container.querySelector('[data-testid="filter-button"]')
		).toBeTruthy();
		expect(
			container.querySelector('[data-testid="order-button"]')
		).toBeTruthy();
	});

	it('renders as disabled', () => {
		const {getByTestId} = render(
			<FilterAndOrder
				disabled
				filterByOptions={FILTER_BY_OPTIONS}
				orderByOptions={ORDER_BY_OPTIONS}
			/>
		);

		expect(getByTestId('filter-button')).toHaveAttribute('disabled');

		expect(getByTestId('order-button')).toHaveAttribute('disabled');
	});

	it('renders filters as a flat list', () => {
		const {container} = render(
			<FilterAndOrder filterByOptions={FILTER_BY_OPTIONS} flat />
		);

		expect(
			container.querySelector('[data-testid="filter-button"]')
		).toBeTruthy();
	});
});
