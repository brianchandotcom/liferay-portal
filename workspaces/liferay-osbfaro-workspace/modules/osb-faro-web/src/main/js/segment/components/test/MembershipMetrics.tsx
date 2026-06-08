/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {BrowserRouter} from 'react-router-dom';
import mockStore from '~/test/mock-store';

import MembershipMetrics from '../MembershipMetrics';

jest.unmock('react-dom');

jest.mock('~/shared/hooks/useRequest', () => ({
	useRequest: jest.fn(() => ({
		data: {
			averageSegmentMembershipDurationMetric: {
				metricType: 'MEMBERSHIP',
				previousValue: 1.2123e8,
				previousValueKey: null,
				trend: {percentage: 258.3, trendClassification: 'POSITIVE'},
				value: 14.3436e8,
				valueKey: null,
			},
			entryRateMetric: {
				metricType: 'MEMBERSHIP',
				previousValue: 5.0,
				previousValueKey: null,
				trend: {percentage: -70.0, trendClassification: 'NEGATIVE'},
				value: 121.0,
				valueKey: null,
			},
			exitRateMetric: {
				metricType: 'MEMBERSHIP',
				previousValue: 1.0,
				previousValueKey: null,
				trend: {percentage: 0.0, trendClassification: 'NEUTRAL'},
				value: 10.0,
				valueKey: null,
			},
			totalMembersMetric: {
				metricType: 'MEMBERSHIP',
				previousValue: 1.0,
				previousValueKey: null,
				totalIndividuals: 100,
				trend: {percentage: 0.0, trendClassification: 'NEUTRAL'},
				value: 34.0,
				valueKey: null,
			},
		},
	})),
}));

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({groupId: '123', id: '456'}),
}));

describe('MembershipMetrics', () => {
	it('renders', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<MembershipMetrics />
				</BrowserRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
