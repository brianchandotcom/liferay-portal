/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {Routes} from '~/shared/util/router';
import * as data from '~/test/data';
import {
	mockRecommendationJobRunsReq,
	mockRecommendationReq,
} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import View from '../View';

jest.unmock('react-dom');

jest.mock('~/shared/hooks/useTimeZone', () => ({
	useTimeZone: () => ({
		timeZoneId: 'UTC',
	}),
}));

const DefaultComponent = (props) => (
	<Provider store={mockStore()}>
		<MemoryRouter
			initialEntries={['/workspace/123/settings/recommendations/321']}
		>
			<Route path={Routes.SETTINGS_RECOMMENDATION_MODEL_VIEW}>
				<MockedProvider
					mocks={[
						mockRecommendationJobRunsReq([
							data.mockRecommendationJobRun(0),
						]),
						mockRecommendationReq(
							data.mockRecommendationJob('321', {
								nextRunDate: new Date().getTime(),
							})
						),
					]}
				>
					<View
						router={{params: {groupId: '123', jobId: '321'}}}
						{...props}
					/>
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('View', () => {
	it('renders', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
