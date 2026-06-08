/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {times} from 'lodash';
import React from 'react';
import {SelectionProvider} from '~/shared/context/selection';
import {mockIndividual} from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import {withStaticRouter} from '~/test/mock-router';

import SearchableEntityTable from '../SearchableEntityTable';

jest.unmock('react-dom');

const COLUMNS = [
	{
		accessor: 'name',
		className: 'table-cell-expand',
		label: 'Name',
		title: true,
	},
	{
		accessor: 'properties.salary',
		label: 'Salary',
	},
	{
		accessor: 'properties.jobTitle',
		label: 'Job Title',
	},
	{
		accessor: 'properties.country',
		label: 'Country',
	},
	{
		accessor: 'properties.age',
		label: 'Age',
	},
];

const TOTAL = 5;
const INDIVIDUALS = times(TOTAL, (i) => mockIndividual(i));

const DefaultComponent = withStaticRouter(SearchableEntityTable);

describe('SearchableEntityTable', () => {
	afterEach(cleanup);

	it('renders table rows with data after loading', async () => {
		const {container} = render(
			<DefaultComponent
				columns={COLUMNS}
				dataSourceFn={() =>
					Promise.resolve({items: INDIVIDUALS, total: TOTAL})
				}
				groupId="23"
				rowIdentifier="id"
			/>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(container.querySelectorAll('tbody tr').length).toBe(TOTAL);
		expect(container.querySelector('tbody tr td').textContent).toBe(
			'Foo Bar'
		);
	});

	it('renders w/ Checkboxes', async () => {
		const {container} = render(
			<SelectionProvider>
				<DefaultComponent
					columns={COLUMNS}
					dataSourceFn={() =>
						Promise.resolve({items: INDIVIDUALS, total: TOTAL})
					}
					groupId="23"
					rowIdentifier="id"
					showCheckbox
				/>
			</SelectionProvider>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(
			container.querySelectorAll('tr.clickable input[type=checkbox]')
				.length
		).toBe(5);
	});

	it('renders loading state without ghost table', () => {
		const {container} = render(
			<DefaultComponent
				columns={COLUMNS}
				dataSourceFn={() => new Promise(() => {})}
				groupId="23"
				rowIdentifier="id"
			/>
		);

		expect(container.querySelector('.loading-root')).toBeTruthy();
		expect(container.querySelector('table')).toBeFalsy();
	});

	it('renders as loading if overrideLoading is true, even if loading is false', () => {
		const {container} = render(
			<SelectionProvider>
				<DefaultComponent
					columns={COLUMNS}
					dataSourceFn={() =>
						Promise.resolve({items: INDIVIDUALS, total: TOTAL})
					}
					groupId="23"
					overrideLoading
					rowIdentifier="id"
					showCheckbox
				/>
			</SelectionProvider>
		);

		jest.runAllTimers();
		expect(container.querySelector('.loading-root')).toBeTruthy();
	});
});
