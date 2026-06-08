import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route, Switch} from 'react-router-dom';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import ReportIssue from '../ReportIssue';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

jest.unmock('react-dom');

describe('ReportIssue', () => {
	it('renders', async () => {
		const {container, queryByText} = render(
			<Provider store={mockStore()}>
				<MemoryRouter initialEntries={['/workspace/23']}>
					<Switch>
						<Route path="/workspace/:groupId">
							<MockedProvider
								cache={
									new InMemoryCache({
										addTypename: false,
									})
								}
								mocks={[]}
							>
								<ReportIssue
									onClose={jest.fn()}
									onNext={jest.fn()}
								/>
							</MockedProvider>
						</Route>
					</Switch>
				</MemoryRouter>
			</Provider>
		);

		await waitForLoadingToBeRemoved(container);

		expect(queryByText('Issue Title')).toBeTruthy();
		expect(queryByText('Description')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});
});
