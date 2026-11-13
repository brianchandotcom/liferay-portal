import * as API from 'shared/api';
import React from 'react';
import TopCampaigns from '../TopCampaigns';
import {render, screen, waitFor} from '@testing-library/react';
import {useParams} from 'react-router-dom';

jest.unmock('react-dom');

let lastFrontendDataSetProps: any;

jest.mock('@liferay/frontend-data-set-web', () => ({
	...jest.requireActual('@liferay/frontend-data-set-web'),
	FrontendDataSet: (props: any) => {
		lastFrontendDataSetProps = props;

		return <div data-testid="fds-component" />;
	},
}));

jest.mock('shared/components/dropdown-range-key/DropdownRangeKey', () => ({
	DropdownRangeKey: () => <div data-testid="dropdown-range-key" />,
}));

jest.mock('shared/hooks/useHistoryAdapter', () => ({
	useHistoryAdapter: () => ({push: jest.fn()}),
}));

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useLocation: () => ({search: ''}),
	useParams: jest.fn(),
}));

const fetchTopCampaigns = API.campaigns.fetchTopCampaigns as jest.Mock;
const mockedUseParams = useParams as jest.Mock;

const getViewAllHref = () =>
	screen.getByRole('link', {name: 'View All'}).getAttribute('href')!;

describe('TopCampaigns', () => {
	beforeEach(() => {
		fetchTopCampaigns.mockClear();
		lastFrontendDataSetProps = undefined;

		mockedUseParams.mockReturnValue({
			channelId: '456',
			groupId: '23',
			id: 'acc-1',
		});
	});

	it('should request the top campaigns of the account', async () => {
		render(<TopCampaigns accountName="IQVIA" />);

		await waitFor(() =>
			expect(fetchTopCampaigns).toHaveBeenCalledWith(
				expect.objectContaining({
					accountId: 'acc-1',
					channelId: '456',
					groupId: '23',
				})
			)
		);
	});

	it('should list the campaigns in the order the backend ranked them', async () => {
		const campaigns = [
			{
				campaignName: 'Spring Launch',
				id: 'c-2',
				individualsTouched: 8,
				lastTouchDate: '2026-11-01',
			},
			{
				campaignName: 'Webinar',
				id: 'c-1',
				individualsTouched: 3,
				lastTouchDate: '2026-11-05',
			},
		];

		fetchTopCampaigns.mockResolvedValueOnce(campaigns);

		render(<TopCampaigns accountName="IQVIA" />);

		await screen.findByTestId('fds-component');

		expect(lastFrontendDataSetProps.items).toEqual(campaigns);
		expect(lastFrontendDataSetProps.apiURL).toBeUndefined();
		expect(lastFrontendDataSetProps.sorts).toBeUndefined();
	});

	it('should not request the top campaigns while the account id is missing', () => {
		mockedUseParams.mockReturnValue({channelId: '456', groupId: '23'});

		render(<TopCampaigns accountName="IQVIA" />);

		expect(fetchTopCampaigns).not.toHaveBeenCalled();
	});

	it('should render the card title', () => {
		render(<TopCampaigns accountName="IQVIA" />);

		expect(screen.getByText('TOP CAMPAIGNS')).toBeInTheDocument();
	});

	it('should link to the campaigns filtered by the account', () => {
		render(<TopCampaigns accountName="IQVIA" />);

		const href = getViewAllHref();

		expect(href).toContain('/campaigns');
		expect(href).not.toContain('/campaigns/');
		expect(href).toContain('accountId=acc-1');
		expect(href).toContain('accountName=IQVIA');
	});

	it('should leave the account name off while it is loading', () => {
		render(<TopCampaigns />);

		expect(getViewAllHref()).not.toContain('accountName');
	});

	it('should render neither the list nor the link while the account id is missing', () => {
		mockedUseParams.mockReturnValue({channelId: '456', groupId: '23'});

		render(<TopCampaigns accountName="IQVIA" />);

		expect(screen.queryByTestId('fds-component')).not.toBeInTheDocument();
		expect(
			screen.queryByRole('link', {name: 'View All'})
		).not.toBeInTheDocument();
	});
});
