/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router-dom';
import Form from '~/shared/components/form';
import {
	JobRunDataPeriods,
	JobRunFrequencies,
	JobTypes,
} from '~/shared/util/constants';
import * as data from '~/test/data';
import {
	mockRecommendationActivitiesReq,
	mockRecommendationPageAssetsReq,
} from '~/test/graphql-data';

import Summary from '../Summary';

jest.unmock('react-dom');

describe('Summary', () => {
	it('renders', () => {
		const {container} = render(
			<MockedProvider
				mocks={[
					mockRecommendationPageAssetsReq([], {size: 0}),
					mockRecommendationActivitiesReq([]),
					mockRecommendationActivitiesReq([], {rangeKey: 60}),
				]}
			>
				<StaticRouter>
					<Form
						initialValues={{
							itemFilters: [
								{
									id: 'includeFilter - url ~ .*custom-assets',
									name: 'includeFilter',
									value: 'url ~ .*custom-assets',
								},
							],
							name: 'Test Name',
							runDataPeriod: JobRunDataPeriods.Last30Days,
							runFrequency: JobRunFrequencies.Manual,
							type: JobTypes.ItemSimilarity,
						}}
					>
						{({initialValues, values}) => (
							<Form.Form>
								<Summary
									initialValues={initialValues}
									runDate={data.getTimestamp()}
									setFieldValue={jest.fn()}
									{...values}
								/>
							</Form.Form>
						)}
					</Form>
				</StaticRouter>
			</MockedProvider>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders with "Including Previous Period" if includePreviousPeriod is true', () => {
		const {queryByText} = render(
			<MockedProvider
				mocks={[
					mockRecommendationPageAssetsReq([], {size: 0}),
					mockRecommendationActivitiesReq([]),
					mockRecommendationActivitiesReq([], {rangeKey: 60}),
				]}
			>
				<StaticRouter>
					<Form
						initialValues={{
							includePreviousPeriod: true,
							itemFilters: [
								{
									id: 'includeFilter - url ~ .*custom-assets',
									name: 'includeFilter',
									value: 'url ~ .*custom-assets',
								},
							],
							name: 'Test Name',
							runDataPeriod: JobRunDataPeriods.Last30Days,
							runFrequency: JobRunFrequencies.Manual,
							type: JobTypes.ItemSimilarity,
						}}
					>
						{({initialValues, values}) => (
							<Form.Form>
								<Summary
									initialValues={initialValues}
									setFieldValue={jest.fn()}
									trainingDate={data.getTimestamp()}
									{...values}
								/>
							</Form.Form>
						)}
					</Form>
				</StaticRouter>
			</MockedProvider>
		);

		expect(queryByText(/Including Previous Period/)).toBeTruthy();
	});
});
