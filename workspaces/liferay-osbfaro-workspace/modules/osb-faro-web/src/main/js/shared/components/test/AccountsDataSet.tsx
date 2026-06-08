/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render, screen} from '@testing-library/react';
import React from 'react';
import {LifecycleStages} from '~/contacts/pages/account/utils/constants';
import {useRequest} from '~/shared/hooks/useRequest';
import {RangeKeyTimeRanges} from '~/shared/util/constants';

import AccountsDataSet from '../AccountsDataSet';

const defaultRangeSelectors = {
	rangeEnd: null,
	rangeKey: RangeKeyTimeRanges.Last30Days,
	rangeStart: null,
};

const DEFAULT_STAGE_ITEMS = [
	{id: '9990', stageType: LifecycleStages.AWARE},
	{id: '9991', stageType: LifecycleStages.ENGAGED},
	{id: '9992', stageType: LifecycleStages.PIPELINE},
	{id: '9993', stageType: LifecycleStages.AT_RISK},
];

jest.unmock('react-dom');

jest.mock('~/shared/api', () => ({
	accounts: {
		fetchLifecycleStageFieldValues: jest.fn(),
	},
}));

jest.mock('~/shared/hooks/useRequest', () => ({
	useRequest: jest.fn(),
}));

const mockedUseRequest = useRequest as jest.Mock;

const mockStages = (items: typeof DEFAULT_STAGE_ITEMS | undefined) => {
	mockedUseRequest.mockReturnValue({
		data: items ? {items} : undefined,
		loading: false,
	});
};

type FakeFilter = {
	id: string;
	items?: Array<{label: string; value: string}>;
	preloadedData?: {
		exclude: boolean;
		selectedItems: Array<{label?: string; value: string}>;
	};
};

type FakeCustomDataRenderers = {
	accountActivityStatusRenderer: (props: {
		value: string;
	}) => React.ReactElement | null | false;
	accountNameRenderer: (props: {
		itemData: {id: string | number};
		value: string;
	}) => React.ReactElement;
};

let lastApiURL: string | undefined;
let lastCustomDataRenderers: FakeCustomDataRenderers | undefined;
let lastFilters: FakeFilter[] | undefined;

jest.mock('@liferay/frontend-data-set-web', () => ({
	...jest.requireActual('@liferay/frontend-data-set-web'),
	FrontendDataSet: ({
		apiURL,
		customDataRenderers,
		filters,
		id,
	}: {
		apiURL: string;
		customDataRenderers: FakeCustomDataRenderers;
		filters: FakeFilter[];
		id: string;
	}) => {
		lastApiURL = apiURL;
		lastCustomDataRenderers = customDataRenderers;
		lastFilters = filters;

		return <div data-testid="fds-component" id={id} />;
	},
}));

describe('AccountsDataSet', () => {
	beforeEach(() => {
		jest.clearAllMocks();
		mockStages(DEFAULT_STAGE_ITEMS);
		lastApiURL = undefined;
		lastCustomDataRenderers = undefined;
		lastFilters = undefined;
	});

	afterEach(cleanup);

	it('renders the FrontendDataSet with id "accounts-list-dataset"', () => {
		render(
			<AccountsDataSet
				apiURL="fake-url"
				channelId="123"
				groupId="23"
				rangeSelectors={defaultRangeSelectors}
			/>
		);

		expect(screen.getByTestId('fds-component')).toHaveAttribute(
			'id',
			'accounts-list-dataset'
		);
	});

	it('leaves activityStatus/country/industry filters without preloadedData when no props are passed', () => {
		render(
			<AccountsDataSet
				apiURL="fake-url"
				channelId="123"
				groupId="23"
				rangeSelectors={defaultRangeSelectors}
			/>
		);

		const activityStatusFilter = lastFilters?.find(
			(f) => f.id === 'activityStatus'
		);
		const countryFilter = lastFilters?.find((f) => f.id === 'country');
		const industryFilter = lastFilters?.find((f) => f.id === 'industry');

		expect(activityStatusFilter?.preloadedData).toBeUndefined();
		expect(countryFilter?.preloadedData).toBeUndefined();
		expect(industryFilter?.preloadedData).toBeUndefined();
	});

	it('omits the lifecycleStatus filter when accountLifecycleId is not provided', () => {
		render(
			<AccountsDataSet
				apiURL="fake-url"
				channelId="123"
				groupId="23"
				rangeSelectors={defaultRangeSelectors}
			/>
		);

		const lifecycleStatusFilter = lastFilters?.find(
			(f) => f.id === 'lifecycleStatus'
		);

		expect(lifecycleStatusFilter).toBeUndefined();
	});

	it('preloads the activityStatus filter when activityStatusFilter prop is provided', () => {
		render(
			<AccountsDataSet
				activityStatusFilter="ACTIVE"
				apiURL="fake-url"
				channelId="123"
				groupId="23"
				rangeSelectors={defaultRangeSelectors}
			/>
		);

		const activityStatusFilter = lastFilters?.find(
			(f) => f.id === 'activityStatus'
		);

		expect(activityStatusFilter?.preloadedData).toEqual({
			exclude: false,
			selectedItems: [{label: 'Active', value: 'ACTIVE'}],
		});
	});

	it('preloads the country filter when countryFilter prop is provided', () => {
		render(
			<AccountsDataSet
				apiURL="fake-url"
				channelId="123"
				countryFilter="US"
				groupId="23"
				rangeSelectors={defaultRangeSelectors}
			/>
		);

		const countryFilter = lastFilters?.find((f) => f.id === 'country');

		expect(countryFilter?.preloadedData).toEqual({
			exclude: false,
			selectedItems: [{label: 'US', value: 'US'}],
		});
	});

	it('preloads the industry filter when industryFilter prop is provided', () => {
		render(
			<AccountsDataSet
				apiURL="fake-url"
				channelId="123"
				groupId="23"
				industryFilter="Tech"
				rangeSelectors={defaultRangeSelectors}
			/>
		);

		const industryFilter = lastFilters?.find((f) => f.id === 'industry');

		expect(industryFilter?.preloadedData).toEqual({
			exclude: false,
			selectedItems: [{label: 'Tech', value: 'Tech'}],
		});
	});

	it('builds the lifecycleStatus items from the fetched stages with localized labels', () => {
		render(
			<AccountsDataSet
				accountLifecycleId="al-1"
				apiURL="fake-url"
				channelId="123"
				groupId="23"
				rangeSelectors={defaultRangeSelectors}
			/>
		);

		const lifecycleStatusFilter = lastFilters?.find(
			(f) => f.id === 'lifecycleStatus'
		);

		expect(lifecycleStatusFilter?.items).toEqual([
			{label: 'Aware', value: '9990'},
			{label: 'Engaged', value: '9991'},
			{label: 'Pipeline', value: '9992'},
			{label: 'At Risk', value: '9993'},
		]);
	});

	it('preloads the lifecycleStatus filter with the stage id and localized label when lifecycleStageFilter prop is provided', () => {
		render(
			<AccountsDataSet
				accountLifecycleId="al-1"
				apiURL="fake-url"
				channelId="123"
				groupId="23"
				lifecycleStageFilter={LifecycleStages.AT_RISK}
				rangeSelectors={defaultRangeSelectors}
			/>
		);

		const lifecycleStatusFilter = lastFilters?.find(
			(f) => f.id === 'lifecycleStatus'
		);

		expect(lifecycleStatusFilter?.preloadedData).toEqual({
			exclude: false,
			selectedItems: [{label: 'At Risk', value: '9993'}],
		});
	});

	it('leaves the lifecycleStatus preloadedData undefined when stages have not loaded yet', () => {
		mockStages(undefined);

		render(
			<AccountsDataSet
				accountLifecycleId="al-1"
				apiURL="fake-url"
				channelId="123"
				groupId="23"
				lifecycleStageFilter={LifecycleStages.AT_RISK}
				rangeSelectors={defaultRangeSelectors}
			/>
		);

		const lifecycleStatusFilter = lastFilters?.find(
			(f) => f.id === 'lifecycleStatus'
		);

		expect(lifecycleStatusFilter?.preloadedData).toBeUndefined();
	});

	it('renders the account name link with channelId in the href', () => {
		render(
			<AccountsDataSet
				apiURL="fake-url"
				channelId="123"
				groupId="23"
				rangeSelectors={defaultRangeSelectors}
			/>
		);

		const {container} = render(
			lastCustomDataRenderers!.accountNameRenderer({
				itemData: {id: 'abc'},
				value: 'Acme Corp',
			})
		);

		expect(container.querySelector('a')).toHaveAttribute(
			'href',
			'/workspace/23/123/contacts/accounts/abc'
		);
	});

	it('appends rangeKey as query param when a preset range is provided', () => {
		render(
			<AccountsDataSet
				apiURL="fake-url"
				channelId="123"
				groupId="23"
				rangeSelectors={{
					rangeEnd: null,
					rangeKey: RangeKeyTimeRanges.Last30Days,
					rangeStart: null,
				}}
			/>
		);

		expect(lastApiURL).toBe('fake-url?rangeKey=30');
	});

	it('appends rangeStart and rangeEnd as query params when a custom range is provided', () => {
		render(
			<AccountsDataSet
				apiURL="fake-url"
				channelId="123"
				groupId="23"
				rangeSelectors={{
					rangeEnd: '2024-01-31',
					rangeKey: RangeKeyTimeRanges.CustomRange,
					rangeStart: '2024-01-01',
				}}
			/>
		);

		expect(lastApiURL).toBe(
			'fake-url?rangeKey=CUSTOM&rangeEnd=2024-01-31&rangeStart=2024-01-01'
		);
	});

	it('renders "Active" label for ACTIVE activity status', () => {
		render(
			<AccountsDataSet
				apiURL="fake-url"
				channelId="123"
				groupId="23"
				rangeSelectors={defaultRangeSelectors}
			/>
		);

		const {getByText} = render(
			lastCustomDataRenderers!.accountActivityStatusRenderer({
				value: 'ACTIVE',
			}) as React.ReactElement
		);

		expect(getByText('Active')).toBeInTheDocument();
	});

	it('renders "Inactive" label for INACTIVE activity status', () => {
		render(
			<AccountsDataSet
				apiURL="fake-url"
				channelId="123"
				groupId="23"
				rangeSelectors={defaultRangeSelectors}
			/>
		);

		const {getByText} = render(
			lastCustomDataRenderers!.accountActivityStatusRenderer({
				value: 'INACTIVE',
			}) as React.ReactElement
		);

		expect(getByText('Inactive')).toBeInTheDocument();
	});

	it('renders nothing when activity status value is empty', () => {
		render(
			<AccountsDataSet
				apiURL="fake-url"
				channelId="123"
				groupId="23"
				rangeSelectors={defaultRangeSelectors}
			/>
		);

		const result = lastCustomDataRenderers!.accountActivityStatusRenderer({
			value: '',
		});

		expect(result).toBeFalsy();
	});
});
