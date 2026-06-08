/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {act, fireEvent, render, waitFor} from '@testing-library/react';
import {range} from 'lodash';
import React from 'react';
import {Provider} from 'react-redux';
import {OrderByDirections} from '~/shared/util/constants';
import {DISPLAY_NAME} from '~/shared/util/pagination';
import * as data from '~/test/data';
import {mockEventAttributeDefinitionsReq} from '~/test/graphql-data';
import mockStore from '~/test/mock-store';

import AttributeBreakdownDropdown from '../index';

jest.unmock('react-dom');

describe('AttributeBreakdownDropdown', () => {
	const defaultMocks = [
		mockEventAttributeDefinitionsReq(
			range(10).map((i) =>
				data.mockEventAttributeDefinition(i, {
					__typename: 'EventAttributeDefinition',
				})
			),
			{
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
			<MockedProvider addTypename={false} mocks={mocks}>
				<AttributeBreakdownDropdown
					disabledIds={[]}
					onAttributeSelect={jest.fn()}
					trigger={<button data-testid="target">click me</button>}
					uneditableIds={[]}
					{...props}
				/>
			</MockedProvider>
		</Provider>
	);

	it('render', async () => {
		const {container, getByTestId} = render(<WrappedComponent />);

		fireEvent.click(getByTestId('target'));

		await waitFor(() =>
			expect(document.body.querySelector('.loading-animation')).toBeNull()
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
			/>
		);

		fireEvent.click(getByTestId('target'));

		await waitFor(() =>
			expect(document.body.querySelector('.loading-animation')).toBeNull()
		);

		act(() => {
			jest.advanceTimersByTime(250);
		});

		expect(
			document.body.querySelectorAll('.dropdown-item.active')
		).toHaveLength(1);
	});

	it('render w/ disabled attributes', async () => {
		const {getByTestId} = render(
			<WrappedComponent disabledIds={['1', '2']} />
		);

		fireEvent.click(getByTestId('target'));

		await waitFor(() =>
			expect(document.body.querySelector('.loading-animation')).toBeNull()
		);

		act(() => {
			jest.advanceTimersByTime(250);
		});

		expect(
			document.body.querySelectorAll('.dropdown-item.disabled')
		).toHaveLength(2);
	});
});
