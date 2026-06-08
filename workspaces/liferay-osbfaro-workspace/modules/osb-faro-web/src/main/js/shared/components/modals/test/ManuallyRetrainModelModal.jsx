/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import RecommendationJobRunsMonthlyStatisticsQuery from '~/settings/recommendations/queries/RecommendationJobRunsMonthlyStatisticsQuery';
import {mockRecommendationJobRunsMonthlyStatistics} from '~/test/data';
import {waitForLoading} from '~/test/helpers';

import ManuallyRetrainModelModal from '../ManuallyRetrainModelModal';

function mockRecommendationJobRunsMonthlyStatisticsReq() {
	return {
		request: {
			query: RecommendationJobRunsMonthlyStatisticsQuery,
			variables: {
				jobId: '321',
			},
		},
		result: {
			data: {
				jobRunsMonthlyStatistics: {
					__typename: 'JobRunsMonthlyStatistics',
					...mockRecommendationJobRunsMonthlyStatistics(),
				},
			},
		},
	};
}

jest.unmock('react-dom');

describe('ManuallyRetrainModelModal', () => {
	it('renders', async () => {
		const {container} = render(
			<MockedProvider
				mocks={[mockRecommendationJobRunsMonthlyStatisticsReq()]}
			>
				<ManuallyRetrainModelModal job={{id: '321'}} />
			</MockedProvider>
		);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
