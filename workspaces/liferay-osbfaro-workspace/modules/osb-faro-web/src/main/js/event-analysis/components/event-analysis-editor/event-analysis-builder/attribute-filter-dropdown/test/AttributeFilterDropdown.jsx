/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {act, fireEvent, render, waitFor} from '@testing-library/react';
import {range} from 'lodash';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {OrderByDirections} from '~/shared/util/constants';
import {DISPLAY_NAME} from '~/shared/util/pagination';
import * as data from '~/test/data';
import {mockEventAttributeDefinitionsReq} from '~/test/graphql-data';
import mockStore from '~/test/mock-store';

import {AttributesProvider} from '../../../context/attributes';
import AttributeFilterDropdown from '../index';

jest.unmock('react-dom');

describe('AttributeFilterDropdown', () => {
	const defaultMocks = [
		mockEventAttributeDefinitionsReq(
			range(10).map((i) =>
				data.mockEventAttributeDefinition(i, {
					__typename: 'EventAttributeDefinition',
				})
			),
			{
				eventDefinitionId: '3',
				keyword: '',
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
			<MemoryRouter
				initialEntries={['/workspace/123/456/event-analysis/123']}
			>
				<Route path="/workspace/:groupId/:channelId/event-analysis/:id">
					<MockedProvider addTypename={false} mocks={mocks}>
						<AttributesProvider>
							<AttributeFilterDropdown
								eventId="3"
								trigger={
									<button data-testid="target">
										click me
									</button>
								}
								uneditableIds={[]}
								{...props}
							/>
						</AttributesProvider>
					</MockedProvider>
				</Route>
			</MemoryRouter>
		</Provider>
	);

	it('render', async () => {
		const {container, getByTestId} = render(<WrappedComponent />);

		fireEvent.click(getByTestId('target'));

		await waitFor(() =>
			expect(document.body.querySelector('.loading-root')).toBeNull()
		);

		act(() => {
			jest.advanceTimersByTime(250);
		});

		expect(container).toMatchSnapshot();

		const dropdownMenu = document.body.querySelector(
			'.base-dropdown-menu-root'
		);

		expect(dropdownMenu).toBeTruthy();
		expect(dropdownMenu).toMatchSnapshot();

		expect(
			dropdownMenu.querySelectorAll('.dropdown-item.active')
		).toHaveLength(0);
	});

	it('render w/ selected attribute', async () => {
		const {getByTestId} = render(
			<WrappedComponent
				attribute={{
					dataType: 'STRING',
					displayName: 'Filed Ticket',
					id: '4',
					name: 'filedTicket',
				}}
				filter={{
					id: '4',
				}}
			/>
		);

		fireEvent.click(getByTestId('target'));

		await waitFor(() =>
			expect(document.body.querySelector('.loading-root')).toBeNull()
		);

		act(() => {
			jest.advanceTimersByTime(250);
		});

		// When a filter is provided, it should show FilterOptions directly

		expect(
			document.body.querySelector('.attribute-options')
		).toBeInTheDocument();
	});

	it('render w/ disabled attributes', async () => {
		const {getByTestId} = render(
			<WrappedComponent disabledIds={['1', '2']} />
		);

		fireEvent.click(getByTestId('target'));

		await waitFor(() =>
			expect(document.body.querySelector('.loading-root')).toBeNull()
		);

		act(() => {
			jest.advanceTimersByTime(250);
		});

		expect(
			document.body.querySelectorAll('.dropdown-item.disabled')
		).toHaveLength(2);
	});
});
