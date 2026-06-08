/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '~/test/mock-modal';
import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render, waitFor} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {open} from '~/shared/actions/modals';
import * as API from '~/shared/api';
import {OnboardingContext} from '~/shared/context/onboarding';
import {mockMemberUser} from '~/test/data';
import {mockDataSourcesReq} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import withOnboarding from '../WithOnboarding';

jest.unmock('react-dom');

const WrappedComponent = withOnboarding(() => (
	<div>wrapped component text</div>
));

const defaultContext = {
	onboardingTriggered: false,
	setOnboardingTriggered: jest.fn(),
};

const Wrapper = ({
	children,
	mocks = [mockDataSourcesReq()],
	onboardingContext = defaultContext,
	store = mockStore(),
}) => (
	<Provider store={store}>
		<MemoryRouter initialEntries={['/']}>
			<Route path="/">
				<OnboardingContext.Provider value={onboardingContext}>
					<MockedProvider
						cache={new InMemoryCache({addTypename: false})}
						mocks={mocks}
					>
						{children}
					</MockedProvider>
				</OnboardingContext.Provider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('WithOnboarding', () => {
	afterEach(() => {
		cleanup();
		jest.clearAllMocks();
	});

	it('renders the wrapped component', async () => {
		const {container} = render(
			<Wrapper
				mocks={[
					mockDataSourcesReq([
						{
							__typename: 'DataSource',
							id: '123',
							name: 'foo datasource',
							url: 'foo.url',
						},
					]),
				]}
			>
				<WrappedComponent />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container.textContent).toBe('wrapped component text');
	});

	it('triggers the onboarding modal if there are no dxp datasources', async () => {
		const {container} = render(
			<Wrapper>
				<WrappedComponent />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved(container);

		await waitFor(() => expect(open).toBeCalled());
	});

	it('does not trigger the onboarding modal for non-admin users', async () => {
		API.user.fetchCurrentUser.mockReturnValueOnce(
			Promise.resolve(mockMemberUser('23'))
		);

		const {container} = render(
			<Wrapper>
				<WrappedComponent />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved(container);

		expect(open).not.toBeCalled();
	});

	it('does not trigger the onboarding modal if it has already been triggered', async () => {
		const {container} = render(
			<Wrapper
				onboardingContext={{
					...defaultContext,
					onboardingTriggered: true,
				}}
			>
				<WrappedComponent />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved(container);

		expect(open).not.toBeCalled();
	});

	it('does not trigger the onboarding modal when a prop happens to match a field in the SitesDashboardQuery', async () => {
		const {container} = render(
			<Wrapper mocks={[mockDataSourcesReq([], {type: 'DYNAMIC'})]}>
				<WrappedComponent type="DYNAMIC" />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved(container);

		expect(open).not.toBeCalled();
	});
});
