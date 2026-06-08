import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render, waitFor} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import * as API from '~/shared/api';
import mockStore from '~/test/mock-store';

import checkProjectState from '../CheckProjectState';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

jest.mock(
	'~/shared/components/workspaces/SuccessDisplay',
	() => () => 'SuccessDisplay'
);

jest.unmock('react-dom');

const DefaultWrapper = ({children}) => (
	<Provider store={mockStore()}>
		<MemoryRouter initialEntries={['/']}>
			<Route path="/">
				<MockedProvider
					cache={
						new InMemoryCache({
							addTypename: false,
							freezeResults: false,
						})
					}
				>
					{children}
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

const TestComponent = () => <div>component body</div>;

describe('SuccessDisplayIf', () => {
	afterEach(cleanup);

	it('renders the WrappedComponent', () => {
		const WrappedComponent = checkProjectState(TestComponent);

		const {container} = render(
			<DefaultWrapper>
				<WrappedComponent groupId="23" />
			</DefaultWrapper>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders the SuccessDisplay component if the project is not ready', () => {
		const WrappedComponent = checkProjectState(TestComponent);

		const {container} = render(
			<DefaultWrapper>
				<WrappedComponent groupId="24" />
			</DefaultWrapper>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders a scheduled maintenance error page if the project state is "maintenance"', () => {
		const WrappedComponent = checkProjectState(TestComponent);

		const {container} = render(
			<DefaultWrapper>
				<WrappedComponent groupId="25" />
			</DefaultWrapper>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders an unavailable error page if the project state is "unavailable"', () => {
		const WrappedComponent = checkProjectState(TestComponent);

		const {container} = render(
			<DefaultWrapper>
				<WrappedComponent groupId="26" />
			</DefaultWrapper>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders an unavailable error page if the project state is "scheduled"', () => {
		const WrappedComponent = checkProjectState(TestComponent);

		const {container} = render(
			<DefaultWrapper>
				<WrappedComponent groupId="27" />
			</DefaultWrapper>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders activating display if project state is "activating"', () => {
		const WrappedComponent = checkProjectState(TestComponent);

		const {container} = render(
			<DefaultWrapper>
				<WrappedComponent groupId="28" />
			</DefaultWrapper>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders activating display if project state is "deactivated"', () => {
		const WrappedComponent = checkProjectState(TestComponent);

		const {container} = render(
			<DefaultWrapper>
				<WrappedComponent groupId="29" />
			</DefaultWrapper>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders an error page if there was an error fetching the project', async () => {
		API.projects.fetch.mockReturnValue(
			Promise.reject({message: 'foo rejection from server'})
		);

		const WrappedComponent = checkProjectState(TestComponent);

		const {container} = render(
			<DefaultWrapper>
				<WrappedComponent groupId="23" />
			</DefaultWrapper>
		);

		await waitFor(() => {});

		expect(container).toMatchSnapshot();
	});
});
