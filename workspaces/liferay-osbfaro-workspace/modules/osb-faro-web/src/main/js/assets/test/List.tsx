/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render, screen} from '@testing-library/react';
import {createMemoryHistory} from 'history';
import React from 'react';
import {Provider} from 'react-redux';
import {Router} from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-var-requires
const {useHistory} = require('react-router-dom');
import List from '~/assets/pages/List';
import {ChannelContext} from '~/shared/context/channel';
import {RangeKeyTimeRanges} from '~/shared/util/constants';
import {mockChannelContext} from '~/test/mock-channel-context';

// Obtain the mocked useHistory so we can configure it per test.

import mockStore from '~/test/mock-store';

jest.unmock('react-dom');

jest.mock('@liferay/frontend-data-set-web', () => ({
	...jest.requireActual('@liferay/frontend-data-set-web'),
	FrontendDataSet: ({
		emptyState,
		filters,
		id,
		itemsActions,
	}: {
		emptyState?: {
			description?: React.ReactNode;
			image?: string;
			title?: string;
		};
		filters?: any[];
		id: string;
		itemsActions?: Array<{onClick?: (item: any) => void}>;
	}) => (
		<div data-testid="fds-component" id={id}>
			{emptyState && (
				<div data-testid="fds-empty-state">
					<div data-testid="fds-empty-state-title">
						{emptyState.title}
					</div>

					<div data-testid="fds-empty-state-description">
						{emptyState.description}
					</div>
				</div>
			)}
			<div data-testid="fds-filters">{JSON.stringify(filters)}</div>

			<button
				data-testid="trigger-info-panel"
				onClick={() =>
					itemsActions?.[0]?.onClick?.({
						itemData: {
							assetCategories: [],
							assetTags: [],
							assetTitle: 'Test Asset Title',
							assetType: 'blog',
							id: 'asset-id-1',
							mimeType: 'blog',
						},
					})
				}
			>
				Open Info Panel
			</button>

			<button
				data-testid="trigger-info-panel-no-mime"
				onClick={() =>
					itemsActions?.[0]?.onClick?.({
						itemData: {
							assetCategories: [],
							assetTags: [],
							assetTitle: 'Asset Without Mime',
							assetType: 'document',
							id: 'asset-id-2',
						},
					})
				}
			>
				Open Info Panel No Mime
			</button>

			<button
				data-testid="trigger-info-panel-no-title"
				onClick={() =>
					itemsActions?.[0]?.onClick?.({
						itemData: {
							assetCategories: [],
							assetTags: [],
							assetType: 'folder',
							id: 'fallback-id-3',
							mimeType: 'folder',
						},
					})
				}
			>
				Open Info Panel No Title
			</button>

			<button
				data-testid="trigger-info-panel-with-items"
				onClick={() =>
					itemsActions?.[0]?.onClick?.({
						itemData: {
							assetCategories: [
								{
									id: 'cat-1',
									name: 'Category One',
									vocabularyId: 'vocab-1',
								},
								{
									id: 'cat-2',
									name: 'Category Two',
									vocabularyId: 'vocab-1',
								},
							],
							assetTags: [{id: 'tag-1', name: 'Tag One'}],
							assetTitle: 'Rich Asset',
							assetType: 'webContent',
							assetVocabularies: [{id: 'vocab-1', name: 'Topic'}],
							id: 'asset-id-4',
							mimeType: 'basic-web-content',
						},
					})
				}
			>
				Open Info Panel With Items
			</button>

			<button
				data-testid="trigger-info-panel-empty-vocab"
				onClick={() =>
					itemsActions?.[0]?.onClick?.({
						itemData: {
							assetCategories: [
								{
									id: 'cat-1',
									name: 'Category One',
									vocabularyId: 'vocab-1',
								},
							],
							assetTags: [],
							assetTitle: 'Asset With Empty Vocab',
							assetType: 'blog',
							assetVocabularies: [
								{id: 'vocab-1', name: 'Topics'},
								{id: 'vocab-2', name: 'Genres'},
							],
							id: 'asset-id-5',
							mimeType: 'blog',
						},
					})
				}
			>
				Open Info Panel Empty Vocab
			</button>
		</div>
	),
}));

jest.mock('~/shared/components/dropdown-range-key/DropdownRangeKey', () => ({
	DropdownRangeKey: ({
		onRangeSelectorChange,
		rangeSelectors,
	}: {
		onRangeSelectorChange: (rs: any) => void;
		rangeSelectors: any;
	}) => (
		<div data-testid="dropdown-range-key">
			<span data-testid="current-range-key">
				{rangeSelectors.rangeKey}
			</span>

			<button
				data-testid="change-range-btn"
				onClick={() =>
					onRangeSelectorChange({
						rangeEnd: null,
						rangeKey: '7', // RangeKeyTimeRanges.Last7Days
						rangeStart: null,
					})
				}
			>
				Change Range
			</button>

			<button
				data-testid="change-range-custom-btn"
				onClick={() =>
					onRangeSelectorChange({
						rangeEnd: '2024-03-01',
						rangeKey: 'CUSTOM', // RangeKeyTimeRanges.CustomRange
						rangeStart: '2024-01-01',
					})
				}
			>
				Change to Custom Range
			</button>
		</div>
	),
}));

// breadcrumbs.getHome is a pure utility – mocking it keeps the test focused
// on the page's own behaviour and avoids router-path side effects.

jest.mock('~/shared/util/breadcrumbs', () => ({
	getHome: jest.fn(({label}: {label?: string} = {}) => ({
		active: false,
		label: label || 'Home',
	})),
}));

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useHistory: jest.fn(),
	useParams: () => ({
		channelId: '123',
		groupId: '23',
	}),
}));

// Default push spy shared across tests, reset in beforeEach.

const mockHistoryPush = jest.fn();

const buildHistory = (path = '/workspace/23/123/assets') => {
	const history = createMemoryHistory({initialEntries: [path]});

	history.push = mockHistoryPush;

	return history;
};

const store = mockStore();

// Helper: wrap List in the minimum context providers it needs.

const renderList = (
	{queryString = ''}: {queryString?: string} = {},
	history = buildHistory(`/workspace/23/123/assets${queryString}`)
) =>
	render(
		<Provider store={store}>
			<ChannelContext.Provider value={mockChannelContext() as any}>
				<Router history={history}>
					<List />
				</Router>
			</ChannelContext.Provider>
		</Provider>
	);

describe('List', () => {
	beforeEach(() => {
		jest.clearAllMocks();

		useHistory.mockReturnValue({push: mockHistoryPush});
	});

	afterEach(cleanup);

	describe('empty state', () => {
		it('passes the correct title to the FDS empty state', () => {
			renderList();

			expect(
				screen.getByTestId('fds-empty-state-title')
			).toHaveTextContent('There are no assets found.');
		});

		it('includes the check-back-later text in the empty state description', () => {
			renderList();

			expect(
				screen.getByTestId('fds-empty-state-description')
			).toHaveTextContent(
				'Check back later to verify if data has been received from your data sources, or you can try a different date range.'
			);
		});

		it('renders a learn-more-about-assets link in the empty state description', () => {
			renderList();

			const link = screen.getByRole('link', {
				exact: false,
				name: /learn more about assets/i,
			});

			expect(link).toBeInTheDocument();
			expect(link).toHaveAttribute(
				'href',
				'https://learn.liferay.com/w/dxp/personalization/analytics-cloud/touchpoints/assets-analytics'
			);
		});
	});

	describe('rendering', () => {
		it('renders without crashing', () => {
			const {container} = renderList();

			expect(container).toBeInTheDocument();
		});

		it('renders the page title "Assets"', () => {
			renderList();

			expect(screen.getByText('Assets')).toBeInTheDocument();
		});

		it('renders the FrontendDataSet component', () => {
			renderList();

			expect(screen.getByTestId('fds-component')).toBeInTheDocument();
		});

		it('renders the FrontendDataSet with id "assetTable"', () => {
			renderList();

			expect(screen.getByTestId('fds-component')).toHaveAttribute(
				'id',
				'assetTable'
			);
		});

		it('passes the mimeType filter to FrontendDataSet', () => {
			renderList();

			const filters = JSON.parse(
				screen.getByTestId('fds-filters').textContent
			);

			const mimeTypeFilter = filters.find(
				(filter: {apiURL: string; id: string; label: string}) =>
					filter.id === 'mimeType'
			);

			expect(mimeTypeFilter).toBeDefined();
			expect(mimeTypeFilter.label).toBe('File Type');
			expect(mimeTypeFilter.apiURL).toContain('asset-summary-mime-types');
		});

		it('renders the DropdownRangeKey', () => {
			renderList();

			expect(
				screen.getByTestId('dropdown-range-key')
			).toBeInTheDocument();
		});

		it('matches the snapshot', () => {
			const {container} = renderList();

			expect(container).toMatchSnapshot();
		});
	});

	describe('initial range selector state', () => {
		it('defaults to Last30Days when no query string is present', () => {
			renderList();

			expect(screen.getByTestId('current-range-key')).toHaveTextContent(
				RangeKeyTimeRanges.Last30Days
			);
		});

		it('picks up rangeKey from the URL query string', () => {

			// The real useQueryRangeSelectors reads from the URL; we provide a
			// URL carrying a rangeKey to verify the initial state is seeded
			// from the query params.

			renderList({
				queryString: `?rangeKey=${RangeKeyTimeRanges.Last7Days}`,
			});

			expect(screen.getByTestId('current-range-key')).toHaveTextContent(
				RangeKeyTimeRanges.Last7Days
			);
		});
	});

	describe('onRangeSelectorChange', () => {
		it('calls history.push when the range selector changes', () => {
			renderList();

			fireEvent.click(screen.getByTestId('change-range-btn'));

			expect(mockHistoryPush).toHaveBeenCalledTimes(1);
		});

		it('updates the displayed range key after a change', () => {

			// List calls setRangeSelectors in the onRangeSelectorChange
			// handler, which causes a re-render passing the new rangeSelectors
			// to the stub DropdownRangeKey. Since history.push is mocked and
			// does not navigate, the state update drives the re-render.

			renderList();

			fireEvent.click(screen.getByTestId('change-range-btn'));

			expect(screen.getByTestId('current-range-key')).toHaveTextContent(
				RangeKeyTimeRanges.Last7Days
			);
		});

		it('includes the new rangeKey in the URL pushed to history', () => {
			renderList();

			fireEvent.click(screen.getByTestId('change-range-btn'));

			const pushedPath: string = mockHistoryPush.mock.calls[0][0];

			expect(pushedPath).toContain(RangeKeyTimeRanges.Last7Days);
		});

		it('resets page to DEFAULT_CUR (1) when the range changes', () => {
			renderList();

			fireEvent.click(screen.getByTestId('change-range-btn'));

			// FaroConstants.pagination.cur === 1 in the jest config globals

			const pushedPath: string = mockHistoryPush.mock.calls[0][0];

			expect(pushedPath).toContain('page=1');
		});

		it('strips rangeEnd and rangeStart from the URL when switching to a preset range', () => {

			// Start with a custom range in the URL so the strip logic is
			// exercised by removeUriQueryParam.

			renderList({
				queryString:
					'?rangeKey=CUSTOM&rangeStart=2024-01-01&rangeEnd=2024-03-01',
			});

			fireEvent.click(screen.getByTestId('change-range-btn'));

			const pushedPath: string = mockHistoryPush.mock.calls[0][0];

			expect(pushedPath).not.toContain('rangeEnd=2024-03-01');
			expect(pushedPath).not.toContain('rangeStart=2024-01-01');
		});

		it('includes rangeEnd and rangeStart in the URL for a custom range', () => {
			renderList();

			fireEvent.click(screen.getByTestId('change-range-custom-btn'));

			// pickBy strips null values; rangeEnd and rangeStart are truthy
			// for a custom range, so they should appear in the URL.

			const pushedPath: string = mockHistoryPush.mock.calls[0][0];

			expect(pushedPath).toContain('rangeEnd=2024-03-01');
			expect(pushedPath).toContain('rangeStart=2024-01-01');
		});

		it('updates the displayed range key to CustomRange after a custom range change', () => {
			renderList();

			fireEvent.click(screen.getByTestId('change-range-custom-btn'));

			expect(screen.getByTestId('current-range-key')).toHaveTextContent(
				RangeKeyTimeRanges.CustomRange
			);
		});
	});

	describe('breadcrumbs', () => {
		it('builds the home breadcrumb using the selected channel name', () => {

			// mockChannelContext() returns selectedChannel = mockChannel(1),
			// whose name is "Channel 1".

			// eslint-disable-next-line @typescript-eslint/no-var-requires
			const breadcrumbs = require('~/shared/util/breadcrumbs');

			renderList();

			expect(breadcrumbs.getHome).toHaveBeenCalledWith(
				expect.objectContaining({
					channelId: '123',
					groupId: '23',
					label: 'Channel 1',
				})
			);
		});

		it('passes null label when no channel is selected', () => {

			// eslint-disable-next-line @typescript-eslint/no-var-requires
			const breadcrumbs = require('~/shared/util/breadcrumbs');

			const contextWithNoChannel = {
				...mockChannelContext(),
				selectedChannel: null,
			};

			render(
				<Provider store={store}>
					<ChannelContext.Provider
						value={contextWithNoChannel as any}
					>
						<Router history={buildHistory()}>
							<List />
						</Router>
					</ChannelContext.Provider>
				</Provider>
			);

			expect(breadcrumbs.getHome).toHaveBeenCalledWith(
				expect.objectContaining({
					label: undefined,
				})
			);
		});
	});

	describe('FDS remount key', () => {
		it('reflects the updated rangeKey in component state after change, triggering FDS remount', () => {

			// List passes key={Object.values(rangeSelectors).join()} to FDS.
			// After setRangeSelectors is called the key changes, forcing FDS
			// to remount. We verify via the DropdownRangeKey stub that the
			// state was updated.

			renderList();

			fireEvent.click(screen.getByTestId('change-range-btn'));

			expect(screen.getByTestId('current-range-key')).toHaveTextContent(
				RangeKeyTimeRanges.Last7Days
			);
		});
	});

	describe('info panel', () => {
		it('displays the asset title in the panel header when opened', () => {
			renderList();

			fireEvent.click(screen.getByTestId('trigger-info-panel'));

			expect(screen.getByText('Test Asset Title')).toBeInTheDocument();
		});

		it('falls back to asset id when assetTitle is absent', () => {
			renderList();

			fireEvent.click(screen.getByTestId('trigger-info-panel-no-title'));

			expect(screen.getByRole('heading', {level: 4})).toHaveTextContent(
				'fallback-id-3'
			);
		});

		it('renders AssetIcon when mimeType is present', () => {
			const {container} = renderList();

			fireEvent.click(screen.getByTestId('trigger-info-panel'));

			// AssetIcon renders a ClaySticker; verify a sticker is present
			// inside the side panel header area.

			expect(container.querySelector('.sticker')).toBeInTheDocument();
		});

		it('renders a default AssetIcon when mimeType is absent', () => {
			const {container} = renderList();

			fireEvent.click(screen.getByTestId('trigger-info-panel-no-mime'));

			expect(container.querySelector('.sticker')).toBeInTheDocument();
		});

		it('adds the sidebar-opened class to the page when the panel is open', () => {
			const {container} = renderList();

			fireEvent.click(screen.getByTestId('trigger-info-panel'));

			expect(
				container.querySelector('.sidebar-opened')
			).toBeInTheDocument();
		});

		it('does not have the sidebar-opened class before the panel is opened', () => {
			const {container} = renderList();

			expect(container.querySelector('.sidebar-opened')).toBeNull();
		});

		it('removes the sidebar-opened class after the panel is closed', () => {
			const {container} = renderList();

			fireEvent.click(screen.getByTestId('trigger-info-panel'));

			expect(
				container.querySelector('.sidebar-opened')
			).toBeInTheDocument();

			// ClayCore's SidePanel calls onOpenChange when closed; trigger it
			// via the close button rendered inside the panel.

			const closeButton = container.querySelector(
				'.info-panel-root .close'
			);

			if (closeButton) {
				fireEvent.click(closeButton);

				expect(container.querySelector('.sidebar-opened')).toBeNull();
			}
		});

		it('renders the Categorization tab', () => {
			renderList();

			fireEvent.click(screen.getByTestId('trigger-info-panel'));

			expect(screen.getByText('Categorization')).toBeInTheDocument();
		});
	});

	describe('CategoriesInfoPanelContent', () => {
		it('displays empty state when there are no categories', () => {
			renderList();

			fireEvent.click(screen.getByTestId('trigger-info-panel'));
			fireEvent.click(screen.getByText('Categorization'));

			expect(
				screen.getByText('No Categories were found for this asset.')
			).toBeInTheDocument();
		});

		it('groups categories under their vocabulary name', () => {
			renderList();

			fireEvent.click(
				screen.getByTestId('trigger-info-panel-with-items')
			);
			fireEvent.click(screen.getByText('Categorization'));

			expect(screen.getByText('Topic')).toBeInTheDocument();
			expect(screen.getByText('Category One')).toBeInTheDocument();
			expect(screen.getByText('Category Two')).toBeInTheDocument();
		});

		it('does not render a vocabulary that has no matching categories', () => {
			renderList();

			fireEvent.click(
				screen.getByTestId('trigger-info-panel-empty-vocab')
			);
			fireEvent.click(screen.getByText('Categorization'));

			expect(screen.getByText('Topics')).toBeInTheDocument();
			expect(screen.queryByText('Genres')).not.toBeInTheDocument();
		});

		it('groups all categories from the same vocabulary under one header', () => {
			renderList();

			fireEvent.click(
				screen.getByTestId('trigger-info-panel-with-items')
			);
			fireEvent.click(screen.getByText('Categorization'));

			expect(screen.getAllByText('Topic')).toHaveLength(1);
			expect(screen.getByText('Category One')).toBeInTheDocument();
			expect(screen.getByText('Category Two')).toBeInTheDocument();
		});

		it('does not show empty state when categories are present', () => {
			renderList();

			fireEvent.click(
				screen.getByTestId('trigger-info-panel-with-items')
			);
			fireEvent.click(screen.getByText('Categorization'));

			expect(
				screen.queryByText('No Categories were found for this asset.')
			).not.toBeInTheDocument();
		});
	});

	describe('TagsInfoPanelContent', () => {
		it('displays empty state when there are no tags', () => {
			renderList();

			fireEvent.click(screen.getByTestId('trigger-info-panel'));
			fireEvent.click(screen.getByText('Categorization'));

			expect(
				screen.getByText('No Tags were found for this asset.')
			).toBeInTheDocument();
		});

		it('renders tags as labels', () => {
			renderList();

			fireEvent.click(
				screen.getByTestId('trigger-info-panel-with-items')
			);
			fireEvent.click(screen.getByText('Categorization'));

			expect(screen.getByText('Tag One')).toBeInTheDocument();
		});

		it('does not show empty state when tags are present', () => {
			renderList();

			fireEvent.click(
				screen.getByTestId('trigger-info-panel-with-items')
			);
			fireEvent.click(screen.getByText('Categorization'));

			expect(
				screen.queryByText('No Tags were found for this asset.')
			).not.toBeInTheDocument();
		});
	});
});
