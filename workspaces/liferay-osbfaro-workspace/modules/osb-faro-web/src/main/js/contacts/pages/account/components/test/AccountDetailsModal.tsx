/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, cleanup, render, screen} from '@testing-library/react';
import React from 'react';
import ReactDOM from 'react-dom';
import {useRequest} from '~/shared/hooks/useRequest';

import AccountDetailsModal from '../AccountDetailsModal';

jest.unmock('react-dom');

let lastOnItemsPropSearch: ((item: any, query: string) => boolean) | undefined;

jest.mock('@liferay/frontend-data-set-web', () => ({
	...jest.requireActual('@liferay/frontend-data-set-web'),
	FrontendDataSet: ({
		id,
		items,
		onItemsPropSearch,
	}: {
		id: string;
		items: any[];
		onItemsPropSearch?: (item: any, query: string) => boolean;
	}) => {
		lastOnItemsPropSearch = onItemsPropSearch;

		return (
			<div data-testid="fds-component" id={id}>
				{(items ?? []).map((item: any) => (
					<div data-testid="fds-item" key={item.name}>
						{item.name}
					</div>
				))}
			</div>
		);
	},
}));

jest.mock('~/shared/hooks/useRequest', () => ({
	useRequest: jest.fn(),
}));

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({channelId: '456', groupId: '23'}),
}));

const mockedUseRequest = useRequest as jest.Mock;

const mockFields = [
	{
		dataSourceId: 'ds-1',
		dataSourceName: 'Salesforce',
		lastModified: '2024-11-20T08:30:00.000Z',
		name: 'website',
		sourceName: 'Web',
		value: 'https://acme.com',
	},
	{
		dataSourceId: 'ds-1',
		dataSourceName: 'Salesforce',
		lastModified: '2024-10-15T10:00:00.000Z',
		name: 'industry',
		sourceName: 'CRM',
		value: 'Technology',
	},
];

const renderModal = (
	overrides: Partial<{
		accountId: string;
		accountName: string;
		onClose: () => void;
	}> = {}
) => {
	const result = render(
		<AccountDetailsModal
			accountId="abc"
			accountName="Acme Corp"
			onClose={jest.fn()}
			{...overrides}
		/>
	);

	act(() => {
		jest.runAllTimers();
	});

	return result;
};

describe('AccountDetailsModal', () => {
	beforeAll(() => {

		// @ts-ignore

		ReactDOM.createPortal = jest.fn((element) => element);
	});

	beforeEach(() => {
		jest.clearAllMocks();
		lastOnItemsPropSearch = undefined;
		mockedUseRequest.mockReturnValue({data: {items: mockFields}});
	});

	afterEach(cleanup);

	it("renders the modal title with the account name's possessive form", () => {
		renderModal();

		expect(screen.getByText("Acme Corp's Attributes")).toBeInTheDocument();
	});

	it('calls useRequest with the account, channel, and group ids', () => {
		renderModal();

		expect(mockedUseRequest).toHaveBeenCalledWith(
			expect.objectContaining({
				variables: {accountId: 'abc', channelId: '456', groupId: '23'},
			})
		);
	});

	it('renders a row in the data set for each field returned by the request', () => {
		renderModal();

		expect(screen.getByTestId('fds-component')).toBeInTheDocument();

		const items = screen.getAllByTestId('fds-item');

		expect(items).toHaveLength(2);
		expect(items[0]).toHaveTextContent('website');
		expect(items[1]).toHaveTextContent('industry');
	});

	it('renders no rows when the request has no data yet', () => {
		mockedUseRequest.mockReturnValue({data: undefined});

		renderModal();

		expect(screen.queryAllByTestId('fds-item')).toHaveLength(0);
	});

	it('matches items by name when the data set search has a query', () => {
		renderModal();

		const matches = mockFields.filter((field) =>
			lastOnItemsPropSearch!(field, 'industry')
		);

		expect(matches).toHaveLength(1);
		expect(matches[0].name).toBe('industry');
	});

	it('matches the name filter case-insensitively', () => {
		renderModal();

		const matches = mockFields.filter((field) =>
			lastOnItemsPropSearch!(field, 'WEB')
		);

		expect(matches).toHaveLength(1);
		expect(matches[0].name).toBe('website');
	});
});
