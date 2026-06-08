/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {fireEvent, render, waitFor} from '@testing-library/react';
import {range} from 'lodash';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter} from 'react-router-dom';
import {OrderByDirections} from '~/shared/util/constants';
import {DISPLAY_NAME} from '~/shared/util/pagination';
import * as data from '~/test/data';
import {mockEventDefinitionsReq} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import EventDropdown from '../EventDropdown';

jest.unmock('react-dom');

describe('EventDropdown', () => {
	const defaultMocks = [
		mockEventDefinitionsReq(
			range(10).map((i) =>
				data.mockEventDefinition(i, {
					__typename: 'EventDefinition',
				})
			),
			{
				eventType: 'ALL',
				hidden: false,
				keyword: '',
				page: 0,
				size: 200,
				sort: {
					column: DISPLAY_NAME,
					type: OrderByDirections.Ascending,
				},
			}
		),
	];

	const WrappedComponent = ({mocks = defaultMocks, ...props}) => (
		<Provider store={mockStore()}>
			<MemoryRouter>
				<MockedProvider
					cache={
						new InMemoryCache({
							addTypename: false,
							freezeResults: false,
						})
					}
					mocks={mocks}
				>
					<EventDropdown
						onEventChange={jest.fn()}
						trigger={<button data-testid="target">click me</button>}
						{...props}
					/>
				</MockedProvider>
			</MemoryRouter>
		</Provider>
	);

	it('render', async () => {
		const {getByTestId} = render(<WrappedComponent />);

		fireEvent.click(getByTestId('target'));

		await waitFor(() =>
			expect(document.body.querySelector('.dropdown-menu')).toBeTruthy()
		);

		await waitForLoadingToBeRemoved(document.body);

		expect(document.body.querySelector('.dropdown-menu')).toMatchSnapshot();

		const dropdownMenu = document.body.querySelector(
			'.base-dropdown-menu-root'
		);

		expect(
			dropdownMenu.querySelectorAll('.dropdown-item.active')
		).toHaveLength(0);
	});

	it('render with selected event', async () => {
		const {getByTestId} = render(<WrappedComponent eventId="3" />);

		fireEvent.click(getByTestId('target'));

		await waitFor(() =>
			expect(document.body.querySelector('.dropdown-menu')).toBeTruthy()
		);

		await waitForLoadingToBeRemoved(document.body);

		expect(
			document.body.querySelectorAll('.dropdown-item.active')
		).toHaveLength(1);
	});
});
