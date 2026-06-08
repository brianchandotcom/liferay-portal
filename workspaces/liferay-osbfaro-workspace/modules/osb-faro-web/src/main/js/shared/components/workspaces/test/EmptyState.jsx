/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {BrowserRouter} from 'react-router-dom';
import mockStore from '~/test/mock-store';

import EmptyState, {CardEmpty} from '../EmptyState';

jest.unmock('react-dom');

describe('Workspace Empty State', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<EmptyState />
				</BrowserRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});

describe('Workspace Empty State - CardEmpty', () => {
	it('renders CardEmpty', () => {
		const {container} = render(
			<CardEmpty
				buttonProps={{
					label: 'CardEmpty Button Label',
				}}
				description="CardEmpty Description"
				icon="home"
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders a button with the secondary display', () => {
		const {getByText} = render(
			<CardEmpty
				buttonProps={{
					displayType: 'secondary',
					label: 'CardEmpty Button Label',
				}}
				description="CardEmpty Description"
				icon="home"
			/>
		);

		expect(
			getByText('CardEmpty Button Label').classList.contains(
				'btn-secondary'
			)
		).toBeTruthy();
	});

	it('contains a button with onClick', () => {
		let foo = '';

		const {getByText} = render(
			<CardEmpty
				buttonProps={{
					displayType: 'secondary',
					label: 'CardEmpty Button Label',
					onClick: () => {
						foo = 'it was clicked';
					},
				}}
				description="CardEmpty Description"
				icon="home"
			/>
		);

		fireEvent.click(getByText('CardEmpty Button Label'));

		expect(foo).toEqual('it was clicked');
	});

	it('contains a button with href', () => {
		const {getByText} = render(
			<BrowserRouter>
				<CardEmpty
					buttonProps={{
						displayType: 'secondary',
						href: '/workspaces',
						label: 'CardEmpty Button Label',
					}}
					description="CardEmpty Description"
					icon="home"
				/>
			</BrowserRouter>
		);

		expect(getByText('CardEmpty Button Label')).toHaveAttribute(
			'href',
			'/workspaces'
		);
	});
});
