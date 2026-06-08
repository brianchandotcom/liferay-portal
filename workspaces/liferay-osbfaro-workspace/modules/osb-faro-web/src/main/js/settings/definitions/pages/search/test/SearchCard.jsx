/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {
	cleanup,
	fireEvent,
	render,
	screen,
	waitFor,
} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';
import {mockSearchStringListReq} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import SearchCard from '../SearchCard';

jest.unmock('react-dom');

jest.mock('~/shared/hooks/useCurrentUser', () => ({
	useCurrentUser: jest.fn(),
}));

const WrappedComponent = (props) => (
	<Provider store={mockStore()}>
		<MemoryRouter
			initialEntries={['/workspace/23/settings/definitions/search']}
		>
			<Route path="/workspace/:groupId/settings/definitions/search">
				<MockedProvider
					cache={
						new InMemoryCache({
							addTypename: false,
							freezeResults: false,
						})
					}
					mocks={[mockSearchStringListReq()]}
				>
					<SearchCard groupId="23" {...props} />
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('SearchCard', () => {
	afterEach(cleanup);

	it('renders', async () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const {container} = render(<WrappedComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('has a default uneditable field with value of q', async () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		render(<WrappedComponent />);

		await waitForLoadingToBeRemoved();

		expect(screen.getByDisplayValue('q')).toBeDisabled();
	});

	it('removes special characters on fields', async () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		render(<WrappedComponent />);

		await waitForLoadingToBeRemoved();

		const input = screen.getByDisplayValue('jackson');

		fireEvent.change(input, {target: {value: 'jackson@#!'}});
		fireEvent.blur(input);

		await waitFor(() => expect(input.value).toBe('jackson'));
	});

	it('removes every character after equals sign', async () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		render(<WrappedComponent />);

		await waitForLoadingToBeRemoved();

		const input = screen.getByDisplayValue('jackson');

		fireEvent.change(input, {target: {value: 'jackson=testvalue'}});
		fireEvent.blur(input);

		await waitFor(() => expect(input.value).toBe('jackson'));
	});

	it('renders input as disabled when user is not admin', async () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => false}));

		const {container} = render(<WrappedComponent />);

		await waitForLoadingToBeRemoved(container);

		container.querySelectorAll('.query-input input').forEach((element) => {
			expect(element).toBeDisabled();
		});
	});

	it('does not render buttons when user is not admin', async () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => false}));

		const {container} = render(<WrappedComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(
			container.querySelectorAll('.query-card-root button')
		).toHaveLength(0);
	});
});
