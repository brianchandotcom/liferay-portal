/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router-dom';
import {mockRecommendationPageAssetsReq} from '~/test/graphql-data';
import {waitForLoading} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import MatchingPagesModal from '../MatchingPagesModal';

jest.unmock('react-dom');

describe('MatchingPagesModal', () => {
	it('renders', async () => {
		const {container} = render(
			<StaticRouter>
				<MockedProvider mocks={[mockRecommendationPageAssetsReq([])]}>
					<Provider store={mockStore()}>
						<MatchingPagesModal
							itemFilters={[
								{
									name: 'includeFilter',
									value: '.*custom-assets',
								},
							]}
						/>
					</Provider>
				</MockedProvider>
			</StaticRouter>
		);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
