import React from 'react';
import TotalAccounts from '../TotalAccounts';
import {AccountMetricType} from 'contacts/pages/account/utils/types';
import {render} from '@testing-library/react';
import {TrendClassification} from 'segment/types';
import {useRequest} from 'shared/hooks/useRequest';

jest.mock('react-router', () => ({
	...jest.requireActual('react-router'),
	useParams: () => ({channelId: '456'}),
}));

jest.mock('shared/hooks/useRequest', () => ({
	...jest.requireActual('shared/hooks/useRequest'),
	useRequest: jest.fn(),
}));

const mockMetrics = () => {
	(useRequest as jest.Mock).mockReturnValue({
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
	});
};

describe('TotalAccounts', () => {
	it('should render', () => {
		mockMetrics();

		const {container} = render(<TotalAccounts groupId="123" />);

		expect(container).toMatchSnapshot();
	});

	it('should render the period-over-period trend only for the new and active accounts cards', () => {
		mockMetrics();

		const {getAllByText, queryByText} = render(
			<TotalAccounts groupId="123" />
		);

		expect(getAllByText(/vs\. Previous 90 Days/)).toHaveLength(2);
		expect(queryByText('50%')).toBeNull();
		expect(getAllByText('30%')).toHaveLength(1);
		expect(getAllByText('0%')).toHaveLength(1);
	});

	it("should load with empty values when there's no data", () => {
		(useRequest as jest.Mock).mockReturnValue({});

		const {container} = render(<TotalAccounts groupId="123" />);

		expect(container).toMatchSnapshot();
	});

	it('should ask for the metrics of the campaign selected', () => {
		mockMetrics();

		render(<TotalAccounts campaignId="7" groupId="123" />);

		expect(useRequest).toHaveBeenCalledWith(
			expect.objectContaining({
				variables: expect.objectContaining({campaignId: '7'}),
			})
		);
	});
});
