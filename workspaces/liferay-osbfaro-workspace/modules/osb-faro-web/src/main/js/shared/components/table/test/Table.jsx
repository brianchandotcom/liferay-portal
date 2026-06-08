/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import {times} from 'lodash';
import React from 'react';
import {StaticRouter} from 'react-router';
import {mockIndividual} from '~/test/data';

import Table, {getRowIdentifierValue} from '../index';

jest.unmock('react-dom');

const INDIVIDUALS = times(5, (i) => mockIndividual(i));

const COLUMNS = [
	{
		accessor: 'name',
		label: 'Name',
	},
	{
		accessor: 'properties.salary',
		label: 'Salary',
	},
];

describe('Table', () => {
	it('renders', () => {
		const {container} = render(
			<StaticRouter>
				<Table
					columns={COLUMNS}
					items={INDIVIDUALS}
					rowIdentifier="id"
				/>
			</StaticRouter>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders without items', () => {
		const {container} = render(
			<StaticRouter>
				<Table columns={COLUMNS} rowIdentifier="id" />
			</StaticRouter>
		);

		expect(container.querySelector('tbody')).toBeNull();
	});

	it('renders with borders', () => {
		const {container} = render(
			<StaticRouter>
				<Table
					bordered
					columns={COLUMNS}
					items={INDIVIDUALS}
					rowIdentifier="id"
				/>
			</StaticRouter>
		);

		expect(container.querySelector('.table-head-bordered')).toBeTruthy();
	});

	it('renders with nowrap rows', () => {
		const {container} = render(
			<StaticRouter>
				<Table
					columns={COLUMNS}
					items={INDIVIDUALS}
					nowrap
					rowIdentifier="id"
				/>
			</StaticRouter>
		);

		expect(container.querySelector('.table-nowrap')).toBeTruthy();
	});

	it('renders w/ loading', () => {
		const {container} = render(
			<StaticRouter>
				<Table
					columns={COLUMNS}
					items={INDIVIDUALS}
					loading
					rowIdentifier="id"
				/>
			</StaticRouter>
		);

		expect(container.querySelector('.loading-root')).toBeTruthy();
	});

	describe('getRowIdentifierValue', () => {
		it('returns a combination of the items specified in the rowIdentifier', () => {
			expect(
				getRowIdentifierValue(
					{company: 'Testers, Inc.', name: 'Test', title: 'tester'},
					['name', 'title']
				)
			).toBe('Testtester');
		});
	});
});
