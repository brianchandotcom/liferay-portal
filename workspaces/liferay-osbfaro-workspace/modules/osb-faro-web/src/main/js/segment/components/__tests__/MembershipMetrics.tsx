import MembershipMetrics from '../MembershipMetrics';
import mockStore from 'test/mock-store';
import React from 'react';
import {BrowserRouter} from 'react-router-dom';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

jest.mock('shared/hooks/useRequest', () => ({
	useRequest: jest.fn(() => ({
		data: {
			averageSegmentMembershipDurationMetric: {
				metricType: 'MEMBERSHIP',
				previousValue: 1.2123e8,
				previousValueKey: null,
				trend: {percentage: 258.3, trendClassification: 'POSITIVE'},
				value: 14.3436e8,
				valueKey: null
			},
			entryRateMetric: {
				metricType: 'MEMBERSHIP',
				previousValue: 5.0,
				previousValueKey: null,
				trend: {percentage: -70.0, trendClassification: 'NEGATIVE'},
				value: 121.0,
				valueKey: null
			},
			exitRateMetric: {
				metricType: 'MEMBERSHIP',
				previousValue: 1.0,
				previousValueKey: null,
				trend: {percentage: 0.0, trendClassification: 'NEUTRAL'},
				value: 10.0,
				valueKey: null
			},
			totalMembersMetric: {
				metricType: 'MEMBERSHIP',
				previousValue: 1.0,
				previousValueKey: null,
				totalIndividuals: 100,
				trend: {percentage: 0.0, trendClassification: 'NEUTRAL'},
				value: 34.0,
				valueKey: null
			}
		}
	}))
}));

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({groupId: '123', id: '456'})
}));

describe('MembershipMetrics', () => {
	it('should render', () => {
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
