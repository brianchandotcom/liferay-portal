/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {JobRunFrequencies, JobRunStatuses} from '~/shared/util/constants';
import * as data from '~/test/data';
import {mockRecommendationJobRunsReq} from '~/test/graphql-data';
import {waitForLoading} from '~/test/helpers';

import OutputVersionsCard from '../OutputVersionsCard';

jest.unmock('react-dom');

describe('OutputVersionsCard', () => {
	it('renders', async () => {
		const {container} = render(
			<MockedProvider
				mocks={[
					mockRecommendationJobRunsReq([
						data.mockRecommendationJobRun(0),
						data.mockRecommendationJobRun(1, {
							status: JobRunStatuses.Failed,
						}),
						data.mockRecommendationJobRun(2, {
							status: JobRunStatuses.Published,
						}),
						data.mockRecommendationJobRun(3, {
							status: JobRunStatuses.Running,
						}),
					]),
				]}
			>
				<OutputVersionsCard
					jobId="321"
					nextRunDate={new Date()}
					runFrequency={JobRunFrequencies.Every14Days}
				/>
			</MockedProvider>
		);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('renders w/o "Next X" date', async () => {
		const {queryByText} = render(
			<MockedProvider
				mocks={[
					mockRecommendationJobRunsReq([
						data.mockRecommendationJobRun(0),
						data.mockRecommendationJobRun(1, {
							status: JobRunStatuses.Failed,
						}),
						data.mockRecommendationJobRun(2, {
							status: JobRunStatuses.Published,
						}),
						data.mockRecommendationJobRun(3, {
							status: JobRunStatuses.Running,
						}),
					]),
				]}
			>
				<OutputVersionsCard
					runFrequency={JobRunFrequencies.Every14Days}
				/>
			</MockedProvider>
		);

		jest.runAllTimers();

		expect(queryByText(/Next/)).toBeNull();
	});
});
