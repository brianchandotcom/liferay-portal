import IndividualsList from '../IndividualsList';
import IndividualsOverviewCDP from '../IndividualsOverviewCDP';
import mockStore from 'test/mock-store';
import React, {useEffect} from 'react';
import {BrowserRouter} from 'react-router-dom';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';
import {useDataSources} from 'shared/context/dataSources';
import {useQuery} from '@apollo/client';
import {useRequest} from 'shared/hooks/useRequest';

jest.mock('@apollo/client', () => ({
	...jest.requireActual('@apollo/client'),
	useQuery: jest.fn(),
}));

jest.mock('shared/hooks/useRequest', () => ({
	useRequest: jest.fn(),
}));

jest.mock('shared/hooks/useCurrentUser', () => ({
	useCurrentUser: jest.fn(),
}));

jest.mock('shared/context/dataSources', () => ({
	useDataSources: jest.fn(),
}));

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '123',
		groupId: '456',
	}),
}));

jest.mock('../IndividualsList', () => ({
	__esModule: true,
	default: jest.fn(() => null),
}));

const mockedIndividualMetrics = {
	data: {
		individualMetric: {
			__typename: 'IndividualMetric',

			anonymousIndividualsMetric: {
				__typename: 'Metric',
				trend: {
					__typename: 'Trend',
					percentage: 11.123,
					trendClassification: 'POSITIVE',
				},
				value: 5123,
			},
			knownIndividualsMetric: {
				__typename: 'Metric',
				trend: {
					__typename: 'Trend',
					percentage: 28.12,
					trendClassification: 'NEGATIVE',
				},
				value: 1103,
			},
			totalIndividualsMetric: {
				__typename: 'Metric',
				trend: {
					__typename: 'Trend',
					percentage: 30.23,
					trendClassification: 'POSITIVE',
				},
				value: 2120,
			},
		},
	},
};

const renderIndividualsOverviewCDP = () =>
	render(
		<Provider store={mockStore()}>
			<BrowserRouter>
				<IndividualsOverviewCDP />
			</BrowserRouter>
		</Provider>
	);

describe('IndividualsOverviewCDP', () => {
	beforeEach(() => {
		(useRequest as jest.Mock).mockReturnValue({
			data: {total: 1},
			loading: false,
		});
		(useCurrentUser as jest.Mock).mockReturnValue({isAdmin: () => true});
		(useDataSources as jest.Mock).mockReturnValue({empty: false});
	});

	afterEach(() => {
		jest.resetAllMocks();

		window.history.pushState({}, '', '/');
	});

	it('should render Individuals Metrics Cards', () => {
		(useQuery as jest.Mock).mockReturnValue({
			data: mockedIndividualMetrics.data,
			loading: false,
		});

		const {getByText} = renderIndividualsOverviewCDP();

		expect(getByText(/^total individuals$/i)).toBeInTheDocument();
		expect(getByText(/^known individuals$/i)).toBeInTheDocument();
		expect(getByText(/^anonymous individuals$/i)).toBeInTheDocument();
	});

	it('should render the period-over-period trend only for the known and anonymous individuals cards', () => {
		(useQuery as jest.Mock).mockReturnValue({
			data: mockedIndividualMetrics.data,
			loading: false,
		});

		const {getAllByText, queryByText} = renderIndividualsOverviewCDP();

		expect(getAllByText(/vs\. Last 30 Days/)).toHaveLength(2);
		expect(queryByText('30.2%')).toBeNull();
		expect(getAllByText('28.1%')).toHaveLength(1);
		expect(getAllByText('11.1%')).toHaveLength(1);
	});

	it.each([
		{campaignId: undefined, expectedRangeKey: 30, listRangeKey: 90},
		{campaignId: '7', expectedRangeKey: 90, listRangeKey: 90},
		{campaignId: '7', expectedRangeKey: 30, listRangeKey: null},
	])(
		'should ask for the metrics in range key $expectedRangeKey when the list period is $listRangeKey and the campaign is $campaignId',
		({campaignId, expectedRangeKey, listRangeKey}) => {
			(IndividualsList as jest.Mock).mockImplementation(
				({
					onRangeKeyChange,
				}: {
					onRangeKeyChange: (rangeKey: number | null) => void;
				}) => {
					useEffect(() => {
						onRangeKeyChange(listRangeKey);
					}, [onRangeKeyChange]);

					return null;
				}
			);
			(useQuery as jest.Mock).mockReturnValue({
				data: mockedIndividualMetrics.data,
				loading: false,
			});

			if (campaignId) {
				window.history.pushState({}, '', `/?campaignId=${campaignId}`);
			}

			renderIndividualsOverviewCDP();

			expect(useQuery).toHaveBeenLastCalledWith(
				expect.anything(),
				expect.objectContaining({
					variables: expect.objectContaining({
						campaignId,
						rangeKey: expectedRangeKey,
					}),
				})
			);
		}
	);

	it('should hide the trends when a campaign is selected', () => {
		(useQuery as jest.Mock).mockReturnValue({
			data: mockedIndividualMetrics.data,
			loading: false,
		});

		window.history.pushState({}, '', '/?campaignId=7');

		const {queryByText} = renderIndividualsOverviewCDP();

		expect(queryByText(/vs\. Last 30 Days/)).toBeNull();
		expect(queryByText('28.1%')).toBeNull();
		expect(queryByText('11.1%')).toBeNull();
	});

	it('should render a centered loader inside each metric card while metrics are loading', () => {
		(useQuery as jest.Mock).mockReturnValue({
			data: undefined,
			loading: true,
		});

		const {container, queryByText} = renderIndividualsOverviewCDP();

		expect(queryByText(/Individuals$/)).not.toBeInTheDocument();

		expect(container.querySelectorAll('.loading-root')).toHaveLength(3);
	});
});
