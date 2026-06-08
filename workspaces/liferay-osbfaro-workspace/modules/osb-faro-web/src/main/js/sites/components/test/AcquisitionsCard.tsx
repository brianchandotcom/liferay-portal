/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter} from 'react-router-dom';
import BasePage from '~/shared/components/base-page';
import {CompositionTypes, RangeKeyTimeRanges} from '~/shared/util/constants';
import {
	mockAcquisitionsReq,
	mockPreferenceReq,
	mockTimeRangeReq,
} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import AcquisitionsCard from '../AcquisitionsCard';

jest.unmock('react-dom');

const MOCK_CONTEXT = {
	filters: {},
	router: {
		params: {
			channelId: '123',
			groupId: '456',
		},
		query: {
			rangeKey: RangeKeyTimeRanges.Last30Days,
		},
	},
};

const DefaultComponent = () => (
	<Provider store={mockStore()}>
		<BasePage.Context.Provider value={MOCK_CONTEXT}>
			<MemoryRouter>
				<MockedProvider
					addTypename={false}
					mocks={[
						mockTimeRangeReq(),
						mockPreferenceReq(),
						mockAcquisitionsReq(),
					]}
				>
					<AcquisitionsCard
						compositionBagName={CompositionTypes.Acquisitions}
						label="card label"
					/>
				</MockedProvider>
			</MemoryRouter>
		</BasePage.Context.Provider>
	</Provider>
);

describe('AcquisitionsCard', () => {
	it('renders', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
