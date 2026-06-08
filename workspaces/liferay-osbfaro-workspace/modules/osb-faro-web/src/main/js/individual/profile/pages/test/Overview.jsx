/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import {Individual} from '~/shared/util/records';
import * as data from '~/test/data';
import {
	mockEventMetrics,
	mockPreferenceReq,
	mockSessions,
	mockTimeRangeReq,
} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import Overview from '../Overview';

jest.unmock('react-dom');

const variables = {channelId: undefined};

describe('IndividualOverview', () => {
	afterEach(cleanup);

	it('renders', async () => {
		const {container, getByText} = render(
			<MockedProvider
				mocks={[
					mockEventMetrics(variables),
					mockTimeRangeReq(),
					mockPreferenceReq(),
					mockSessions(variables),
				]}
			>
				<Provider store={mockStore()}>
					<StaticRouter>
						<Overview
							groupId="23"
							id="test"
							individual={data.getImmutableMock(
								Individual,
								data.mockIndividual
							)}
						/>
					</StaticRouter>
				</Provider>
			</MockedProvider>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(getByText('View All Details')).toBeInTheDocument();
	});
});
