/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import SearchableList from '../SearchableList';

jest.unmock('react-dom');

describe('BaseDropdownSearchableList', () => {
	const WrappedComponent = (props) => (
		<SearchableList
			items={[
				{
					displayName: 'Test 0',
					id: '0',
					name: 'testName',
					type: 'custom',
				},
				{
					displayName: 'Test 1',
					id: '1',
					name: 'testName1',
					type: 'default',
				},
			]}
			onEditClick={jest.fn()}
			onItemClick={jest.fn()}
			onItemOptionsClick={jest.fn()}
			onQueryChange={jest.fn()}
			query=""
			{...props}
		/>
	);

	it('renders', () => {
		const {container} = render(<WrappedComponent />);

		expect(container.querySelector('.active')).toBeNull();
		expect(container.querySelector('.disabled')).toBeNull();
		expect(container).toMatchSnapshot();
	});

	it('renders with query "1"', () => {
		const {queryByText} = render(<WrappedComponent query="Test 1" />);

		expect(queryByText('Test 1')).toBeTruthy();
		expect(queryByText('Test 0')).toBeNull();
	});

	it('renders with an active item', () => {
		const {container} = render(<WrappedComponent activeId="0" />);

		expect(container.querySelector('.disabled')).toBeNull();
		expect(container.querySelector('.active')).toBeTruthy();
	});

	it('renders with 2 disabled items', () => {
		const {container} = render(
			<WrappedComponent disabledIds={['0', '1']} />
		);

		expect(container.querySelectorAll('.disabled').length).toBe(2);
		expect(container.querySelector('.active')).toBeNull();
	});

	it('hides info card', () => {
		const {container, getByText, rerender} = render(<WrappedComponent />);

		const element = getByText('Test 1');

		fireEvent.mouseOver(element);

		expect(container.querySelector('.info-card-popover-root')).toBeTruthy();

		rerender(<WrappedComponent showInfoCard={false} />);

		fireEvent.mouseOver(element);

		expect(container.querySelector('.info-card-popover-root')).toBeNull();
	});
});
