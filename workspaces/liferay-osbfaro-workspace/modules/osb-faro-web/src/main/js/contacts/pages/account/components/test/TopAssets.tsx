/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	cleanup,
	fireEvent,
	render,
	screen,
	within,
} from '@testing-library/react';
import React from 'react';
import {ITopAsset} from '~/shared/api/assets';
import {useRequest} from '~/shared/hooks/useRequest';

import TopAssets from '../TopAssets';

jest.unmock('react-dom');

jest.mock('~/shared/api', () => ({
	assets: {
		fetchAccountTopAssets: jest.fn(),
	},
}));

jest.mock('~/shared/hooks/useRequest', () => ({
	useRequest: jest.fn(),
}));

const mockPush = jest.fn();

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useHistory: () => ({push: mockPush}),
	useParams: () => ({channelId: '5', groupId: '23', id: 'acc-1'}),
}));

const mockedUseRequest = useRequest as jest.Mock;

const buildAsset = (overrides: Partial<ITopAsset> = {}): ITopAsset => ({
	assetTitle: 'Asset 1',
	assetType: 'webContent',
	downloadsMetric: {value: 5},
	id: 'a-1',
	impressionsMetric: {value: 30},
	mimeType: undefined,
	viewsMetric: {value: 10},
	...overrides,
});

const DEFAULT_ASSETS: ITopAsset[] = [
	buildAsset({
		assetTitle: 'Web Content One',
		assetType: 'webContent',
		downloadsMetric: {value: 1},
		id: 'a-1',
		impressionsMetric: {value: 100},
		viewsMetric: {value: 50},
	}),
	buildAsset({
		assetTitle: 'Blog Post',
		assetType: 'blog',
		downloadsMetric: {value: 2},
		id: 'a-2',
		impressionsMetric: {value: 80},
		viewsMetric: {value: 40},
	}),
	buildAsset({
		assetTitle: 'Brochure PDF',
		assetType: 'document',
		downloadsMetric: {value: 25},
		id: 'a-3',
		impressionsMetric: {value: 30},
		viewsMetric: {value: 12},
	}),
	buildAsset({
		assetTitle: 'Lead Form',
		assetType: 'form',
		downloadsMetric: {value: 0},
		id: 'a-4',
		impressionsMetric: {value: 20},
		viewsMetric: {value: 8},
	}),
	buildAsset({
		assetTitle: 'Custom Entry',
		assetType: 'customObjectEntry',
		downloadsMetric: {value: 0},
		id: 'a-5',
		impressionsMetric: {value: 10},
		viewsMetric: {value: 4},
	}),
];

const mockUseRequestWith = ({
	data,
	loading = false,
}: {
	data?: {items: ITopAsset[]};
	loading?: boolean;
}) => {
	mockedUseRequest.mockImplementation(() => ({
		data,
		loading,
		refetch: jest.fn(),
	}));
};

describe('TopAssets', () => {
	beforeEach(() => {
		jest.clearAllMocks();
		mockUseRequestWith({data: {items: DEFAULT_ASSETS}});
	});

	afterEach(cleanup);

	describe('rendering', () => {
		it('renders the card title', () => {
			render(<TopAssets />);

			expect(screen.getByText('TOP ASSETS')).toBeInTheDocument();
		});

		it('renders both tab labels', () => {
			render(<TopAssets />);

			expect(
				screen.getByRole('tab', {name: 'Content'})
			).toBeInTheDocument();
			expect(
				screen.getByRole('tab', {name: 'Files'})
			).toBeInTheDocument();
		});

		it('renders the View All button', () => {
			render(<TopAssets />);

			expect(
				screen.getByRole('button', {name: 'View All'})
			).toBeInTheDocument();
		});

		it('renders the Group By dropdown with the default metric (Impressions)', () => {
			render(<TopAssets />);

			expect(screen.getAllByText('Group By').length).toBeGreaterThan(0);
			expect(screen.getAllByText('Impressions').length).toBeGreaterThan(
				0
			);
		});
	});

	describe('data rendering', () => {
		it('renders an asset link for every item returned', () => {
			render(<TopAssets />);

			expect(
				screen.getAllByRole('link', {name: 'Web Content One'})[0]
			).toBeInTheDocument();
			expect(
				screen.getAllByRole('link', {name: 'Blog Post'})[0]
			).toBeInTheDocument();
			expect(
				screen.getAllByRole('link', {name: 'Brochure PDF'})[0]
			).toBeInTheDocument();
			expect(
				screen.getAllByRole('link', {name: 'Lead Form'})[0]
			).toBeInTheDocument();
			expect(
				screen.getAllByRole('link', {name: 'Custom Entry'})[0]
			).toBeInTheDocument();
		});

		it('renders the metric values for the default Impressions metric', () => {
			render(<TopAssets />);

			expect(screen.getAllByText('100').length).toBeGreaterThan(0);
			expect(screen.getAllByText('80').length).toBeGreaterThan(0);
			expect(screen.getAllByText('30').length).toBeGreaterThan(0);
			expect(screen.getAllByText('20').length).toBeGreaterThan(0);
			expect(screen.getAllByText('10').length).toBeGreaterThan(0);
		});

		it('routes blog assets through the Blogs overview path', () => {
			render(<TopAssets />);

			const link = screen.getAllByRole('link', {
				name: 'Blog Post',
			})[0] as HTMLAnchorElement;

			expect(link.getAttribute('href')).toContain('/assets/blogs/');
		});

		it('routes document assets through the Documents and Media path', () => {
			render(<TopAssets />);

			const link = screen.getAllByRole('link', {
				name: 'Brochure PDF',
			})[0] as HTMLAnchorElement;

			expect(link.getAttribute('href')).toContain(
				'/assets/documents-and-media/'
			);
		});

		it('routes form assets through the Forms path', () => {
			render(<TopAssets />);

			const link = screen.getAllByRole('link', {
				name: 'Lead Form',
			})[0] as HTMLAnchorElement;

			expect(link.getAttribute('href')).toContain('/assets/forms/');
		});

		it('routes web content assets through the Web Content path', () => {
			render(<TopAssets />);

			const link = screen.getAllByRole('link', {
				name: 'Web Content One',
			})[0] as HTMLAnchorElement;

			expect(link.getAttribute('href')).toContain('/assets/web-content/');
		});

		it('routes unknown asset types through the Object Entry path', () => {
			render(<TopAssets />);

			const link = screen.getAllByRole('link', {
				name: 'Custom Entry',
			})[0] as HTMLAnchorElement;

			expect(link.getAttribute('href')).toContain(
				'/assets/object-entry/'
			);
		});
	});

	describe('group by dropdown', () => {
		it('refetches with viewsMetric when the user picks Views', () => {
			render(<TopAssets />);

			fireEvent.click(
				screen.getAllByRole('button', {name: /Group By/})[0]
			);

			fireEvent.click(
				screen.getAllByRole('menuitem', {name: 'Views'})[0]
			);

			const lastCall =
				mockedUseRequest.mock.calls[
					mockedUseRequest.mock.calls.length - 1
				][0];

			expect(lastCall.variables.selectedMetric).toBe('viewsMetric');
		});

		it('refetches with downloadsMetric when the user picks Downloads on the Files tab', () => {
			render(<TopAssets />);

			fireEvent.click(screen.getByRole('tab', {name: 'Files'}));

			fireEvent.click(
				screen.getAllByRole('button', {name: /Group By/})[0]
			);

			fireEvent.click(
				screen.getAllByRole('menuitem', {name: 'Downloads'})[0]
			);

			const lastCall =
				mockedUseRequest.mock.calls[
					mockedUseRequest.mock.calls.length - 1
				][0];

			expect(lastCall.variables.selectedMetric).toBe('downloadsMetric');
		});

		it('does not offer the Downloads metric on the Content tab', () => {
			render(<TopAssets />);

			fireEvent.click(
				screen.getAllByRole('button', {name: /Group By/})[0]
			);

			expect(
				screen.queryByRole('menuitem', {name: 'Downloads'})
			).toBeNull();
			expect(
				screen.getAllByRole('menuitem', {name: 'Impressions'}).length
			).toBeGreaterThan(0);
			expect(
				screen.getAllByRole('menuitem', {name: 'Views'}).length
			).toBeGreaterThan(0);
		});

		it('offers the Downloads metric on the Files tab', () => {
			render(<TopAssets />);

			fireEvent.click(screen.getByRole('tab', {name: 'Files'}));

			fireEvent.click(
				screen.getAllByRole('button', {name: /Group By/})[0]
			);

			expect(
				screen.getAllByRole('menuitem', {name: 'Downloads'}).length
			).toBeGreaterThan(0);
		});

		it('resets to impressionsMetric when switching to Content while Downloads is selected', () => {
			render(<TopAssets />);

			fireEvent.click(screen.getByRole('tab', {name: 'Files'}));

			fireEvent.click(
				screen.getAllByRole('button', {name: /Group By/})[0]
			);

			fireEvent.click(
				screen.getAllByRole('menuitem', {name: 'Downloads'})[0]
			);

			fireEvent.click(screen.getByRole('tab', {name: 'Content'}));

			const lastCall =
				mockedUseRequest.mock.calls[
					mockedUseRequest.mock.calls.length - 1
				][0];

			expect(lastCall.variables.objectType).toBe('content');
			expect(lastCall.variables.selectedMetric).toBe('impressionsMetric');
		});
	});

	describe('tab switching', () => {
		it('requests the Content objectType on initial render', () => {
			render(<TopAssets />);

			const firstCall = mockedUseRequest.mock.calls[0][0];

			expect(firstCall.variables.objectType).toBe('content');
		});

		it('requests the File objectType after clicking the Files tab', () => {
			render(<TopAssets />);

			fireEvent.click(screen.getByRole('tab', {name: 'Files'}));

			const lastCall =
				mockedUseRequest.mock.calls[
					mockedUseRequest.mock.calls.length - 1
				][0];

			expect(lastCall.variables.objectType).toBe('file');
		});
	});

	describe('request shape', () => {
		it('forwards accountId, channelId, and groupId to the data source', () => {
			render(<TopAssets />);

			const firstCall = mockedUseRequest.mock.calls[0][0];

			expect(firstCall.variables.accountId).toBe('acc-1');
			expect(firstCall.variables.channelId).toBe('5');
			expect(firstCall.variables.groupId).toBe('23');
		});
	});

	describe('view all', () => {
		it('navigates to the asset list when the View All button is clicked', () => {
			render(<TopAssets />);

			fireEvent.click(screen.getByRole('button', {name: 'View All'}));

			expect(mockPush).toHaveBeenCalledTimes(1);
			expect(mockPush.mock.calls[0][0]).toContain('/assets');
		});
	});

	describe('loading state', () => {
		it('renders the loading indicator while the request is in flight', () => {
			mockUseRequestWith({loading: true});

			const {container} = render(<TopAssets />);

			expect(
				container.querySelector('.loading-root')
			).toBeInTheDocument();
		});

		it('does not render asset rows while loading', () => {
			mockUseRequestWith({loading: true});

			render(<TopAssets />);

			expect(
				screen.queryByRole('link', {name: 'Web Content One'})
			).toBeNull();
		});

		it('does not render the View All button while loading', () => {
			mockUseRequestWith({loading: true});

			render(<TopAssets />);

			expect(screen.queryByRole('button', {name: 'View All'})).toBeNull();
		});
	});

	describe('view all visibility', () => {
		it('does not render the View All button when there are no assets', () => {
			mockUseRequestWith({data: {items: []}});

			render(<TopAssets />);

			expect(screen.queryByRole('button', {name: 'View All'})).toBeNull();
		});

		it('renders the View All button when assets are returned', () => {
			render(<TopAssets />);

			expect(
				screen.getByRole('button', {name: 'View All'})
			).toBeInTheDocument();
		});
	});

	describe('empty state', () => {
		it('renders the Content empty state when no assets are returned on the Content tab', () => {
			mockUseRequestWith({data: {items: []}});

			render(<TopAssets />);

			expect(
				screen.getAllByText('No Assets Available').length
			).toBeGreaterThan(0);
		});

		it('renders the Files empty state when no assets are returned on the Files tab', () => {
			mockUseRequestWith({data: {items: []}});

			render(<TopAssets />);

			fireEvent.click(screen.getByRole('tab', {name: 'Files'}));

			expect(
				screen.getAllByText('No Files Available').length
			).toBeGreaterThan(0);
		});

		it('does not render asset rows when no assets are returned', () => {
			mockUseRequestWith({data: {items: []}});

			render(<TopAssets />);

			expect(
				screen.queryByRole('link', {name: 'Web Content One'})
			).toBeNull();
		});
	});

	describe('metric column', () => {
		it('shows the selected metric value in the metric column', () => {
			mockUseRequestWith({
				data: {
					items: [
						buildAsset({
							assetTitle: 'Solo',
							downloadsMetric: {value: 7},
							id: 'a-solo',
							impressionsMetric: {value: 999},
							viewsMetric: {value: 333},
						}),
					],
				},
			});

			const {container} = render(<TopAssets />);

			const tabPanel = container.querySelector(
				'.tab-pane'
			) as HTMLElement;

			expect(within(tabPanel).getAllByText('999').length).toBe(1);
		});
	});
});
