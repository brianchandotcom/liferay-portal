/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {mockIndividual} from '~/test/data';

import Row from '../Row';

jest.unmock('react-dom');

const INDIVIDUAL = mockIndividual();

const INDIVIDUAL_NO_SALARY = mockIndividual(1, {
	salary: null,
});

const COLUMNS = [
	{
		accessor: 'name',
		label: 'Name',
		title: true,
	},
	{
		accessor: 'properties.salary',
		label: 'Salary',
	},
];

describe('Row', () => {
	it('renders', () => {
		const {container} = render(<Row />);

		expect(container).toMatchSnapshot();
	});

	it('renders with data', () => {
		const {container} = render(<Row columns={COLUMNS} data={INDIVIDUAL} />);

		expect(container).toMatchSnapshot();
	});

	it('renders with empty data if the column accessor value is null', () => {
		const {getByText} = render(
			<Row columns={COLUMNS} data={INDIVIDUAL_NO_SALARY} />
		);

		expect(getByText('-')).toBeTruthy();
	});

	it('renders with empty data if the accessor does not exist at all on the object', () => {
		const {getByText} = render(
			<Row
				columns={COLUMNS.concat([
					{accessor: 'nonExistentAccessor', label: 'does not exist'},
				])}
				data={INDIVIDUAL}
			/>
		);

		expect(getByText('-')).toBeTruthy();
	});
});
