/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {AccountMetricType} from '~/contacts/pages/account/utils/types';
import {TrendClassification} from '~/segment/types';

import TotalAccounts from '../TotalAccounts';

jest.unmock('react-dom');

jest.mock('react-router', () => ({
	...jest.requireActual('react-router'),
	useParams: () => ({channelId: '456'}),
}));

describe('TotalAccounts', () => {
	it('renders', () => {
		const useRequest = require('~/shared/hooks/useRequest');
		useRequest.useRequest = jest.fn(() => ({
			data: [
				{
					metricType: AccountMetricType.Total,
					trend: {
						percentage: 50,
						trendClassification: TrendClassification.Positive,
					},
					value: 15,
				},
				{
					metricType: AccountMetricType.New,
					trend: {
						percentage: -30,
						trendClassification: TrendClassification.Negative,
					},
					value: 10,
				},
				{
					metricType: AccountMetricType.Active,
					trend: {
						percentage: 0,
						trendClassification: TrendClassification.Neutral,
					},
					value: 1,
				},
			],
		}));

		const {container} = render(<TotalAccounts groupId="123" />);

		expect(container).toMatchSnapshot();
	});

	it("loads with empty values when there's no data", () => {
		const useRequest = require('~/shared/hooks/useRequest');
		useRequest.useRequest = jest.fn(() => ({}));

		const {container} = render(<TotalAccounts groupId="123" />);

		expect(container).toMatchSnapshot();
	});
});
