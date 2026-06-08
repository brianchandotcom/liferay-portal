/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import CardTabs from '../CardTabs';

jest.unmock('react-dom');

const mockTabs = [
	{
		secondaryInfo: 'Foo secondary info',
		tabId: 'foo',
		title: 'Foo Tab',
	},
	{
		secondaryInfo: 'Bar secondary info',
		tabId: 'bar',
		title: 'Bar Tab',
	},
];

describe('CardTabs', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<CardTabs activeTabId="bar" tabs={mockTabs} />
		);

		expect(container).toMatchSnapshot();
	});

	it('calls onChange prop when a tab is clicked', () => {
		const onChangeSpy = jest.fn();

		const {getByText} = render(
			<CardTabs
				activeTabId="bar"
				onChange={onChangeSpy}
				tabs={mockTabs}
			/>
		);

		fireEvent.click(getByText(/Foo Tab/));

		expect(onChangeSpy).toHaveBeenCalledWith('foo');
	});
});
