/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {CalculationTypes} from '~/event-analysis/utils/types';
import mockStore from '~/test/mock-store';

import EventAnalysisEditor from '../index';

jest.unmock('react-dom');

describe('Event Analysis Editor', () => {
	it('render', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<MemoryRouter>
					<Route path="/">
						<MockedProvider
							cache={
								new InMemoryCache({
									addTypename: false,
									freezeResults: false,
								})
							}
						>
							<EventAnalysisEditor
								type={CalculationTypes.Total}
							/>
						</MockedProvider>
					</Route>
				</MemoryRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
