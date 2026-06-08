/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {mockRecommendationPageAssetsReq} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import NewRuleModal from '../NewRuleModal';

jest.unmock('react-dom');

const DefaultWrapper = ({children, mocks = []}) => (
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
					mocks={mocks}
				>
					{children}
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('NewRuleModal', () => {
	afterEach(cleanup);

	it('renders', async () => {
		const {container} = render(
			<DefaultWrapper mocks={[mockRecommendationPageAssetsReq([])]}>
				<NewRuleModal />
			</DefaultWrapper>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
