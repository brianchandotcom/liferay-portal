/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {act, fireEvent, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter} from 'react-router-dom';
import mockStore from '~/test/mock-store';

import BaseDropdown from '../index';

jest.unmock('react-dom');

const DefaultComponent = (props) => (
	<Provider store={mockStore()}>
		<MemoryRouter>
			<MockedProvider freezeResults={false}>
				<BaseDropdown
					onActiveChange={jest.fn()}
					trigger={<button data-testid="target">click me</button>}
					{...props}
				>
					{() => <div>Child contents</div>}
				</BaseDropdown>
			</MockedProvider>
		</MemoryRouter>
	</Provider>
);

describe('BaseDropdown', () => {
	it('renders', () => {
		const {container, getByTestId} = render(<DefaultComponent />);

		fireEvent.click(getByTestId('target'));

		act(() => {
			jest.advanceTimersByTime(250);
		});

		expect(container).toMatchSnapshot();

		const dropdownMenu = document.body.getElementsByClassName(
			'event-analysis-dropdown-menu-root'
		)[0];

		expect(dropdownMenu).toMatchSnapshot();
	});
});
