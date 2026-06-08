/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import AppliedFilters from '../AppliedFilters';

jest.unmock('react-dom');

const filters = {
	Devices: ['Desktop'],
	Location: ['Brazil'],
};

describe('AppliedFilters', () => {
	it('renders', () => {
		const {container} = render(<AppliedFilters filters={filters} />);

		expect(container).toMatchSnapshot();
	});

	it('renders with no filters applied', () => {
		const {queryByText} = render(<AppliedFilters />);

		expect(queryByText('Brazil')).not.toBeInTheDocument();
	});

	it('deactivates the filter when clicking on btn close label', () => {
		const spy = jest.fn();

		const {container} = render(
			<AppliedFilters filters={filters} onChange={spy} />
		);

		fireEvent.click(container.querySelectorAll('button')[1]);

		expect(spy).toBeCalledWith({Devices: ['Desktop'], Location: []});
	});

	it('deactivates all filters when clicking on "Clear Filter"', () => {
		const spy = jest.fn();

		const {getByText} = render(
			<AppliedFilters filters={filters} onChange={spy} />
		);

		fireEvent.click(getByText('Clear Filter'));

		expect(spy).toBeCalled();
	});
});
